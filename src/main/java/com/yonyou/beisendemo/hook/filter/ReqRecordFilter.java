package com.yonyou.beisendemo.hook.filter;

import cn.hutool.core.date.StopWatch;
import com.yonyou.beisendemo.context.ReqInfoContext;
import com.yonyou.beisendemo.global.GlobalInitService;
import com.yonyou.beisendemo.mdc.MdcUtil;
import com.yonyou.beisendemo.services.user.LoginService;
import com.yonyou.beisendemo.utils.CrossUtil;
import com.yonyou.beisendemo.utils.EnvUtil;
import com.yonyou.beisendemo.utils.IpUtil;
import com.yonyou.beisendemo.utils.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@WebFilter(urlPatterns = "/*" , filterName = "reqRecordFilter" , asyncSupported = true)
public class ReqRecordFilter implements Filter {

    @Autowired
    private GlobalInitService globalInitService;

    /**
     * 返回给前端的traceId，用于日志追踪
     */
    private static final String GLOBAL_TRACE_ID_HEADER = "g-trace-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        request = this.initUserInfo((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        stopWatch.stop();
        stopWatch.start("cors");
        CrossUtil.buildCors(request, (HttpServletResponse) servletResponse);
        stopWatch.stop();
        stopWatch.start("业务执行");
        filterChain.doFilter(request, servletResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public HttpServletRequest initUserInfo(HttpServletRequest req , HttpServletResponse res){
        if(isStaticUri(req)){
            return req;
        }
        StopWatch stopWatch = new StopWatch("请求参数构建");
        try {
            stopWatch.start("traceId");
            // 添加全链路的traceId
            MdcUtil.addTraceId();
            stopWatch.stop();

            stopWatch.start("请求基本信息");
            ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
            reqInfo.setHost(req.getHeader("host"));
            reqInfo.setPath(req.getPathInfo());
            if (reqInfo.getPath() == null) {
                String url = req.getRequestURI();
                int index = url.indexOf("?");
                if (index > 0) {
                    url = url.substring(0, index);
                }
                reqInfo.setPath(url);
            }
            reqInfo.setReferer(req.getHeader("referer"));
            reqInfo.setClientIp(IpUtil.getClientIp(req));
            reqInfo.setUserAgent(req.getHeader("User-Agent"));
            reqInfo.setDeviceId(getOrInitDeviceId(req, res));
            req = this.wrapperRequest(req, reqInfo);
            stopWatch.stop();

            stopWatch.start("登录用户信息");
            // 初始化登录信息
            globalInitService.initLoginUser(reqInfo);
            stopWatch.stop();

            ReqInfoContext.addReqInfo(reqInfo);

            res.setHeader(GLOBAL_TRACE_ID_HEADER , Optional.ofNullable(MdcUtil.getTraceId()).orElse(""));
        }catch (Exception e) {
            log.error("init reqInfo error!", e);
        }finally {
            if (!EnvUtil.isPro()) {
                log.info("{} -> 请求构建耗时: \n{}", req.getRequestURI(), stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
            }
        }
        return req;
    }

    private HttpServletRequest wrapperRequest(HttpServletRequest request, ReqInfoContext.ReqInfo reqInfo) {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            return request;
        }

        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        reqInfo.setPayload(requestWrapper.getBodyString());
        return requestWrapper;
    }

    public boolean isStaticUri(HttpServletRequest req){
        return req.getRequestURI().matches(".*\\.(js|css|png|jpg|gif|ico|woff|woff2|ttf|svg|eot|min.js.map|min.css.map)");
    }

    /**
     * 初始化设备id
     *
     * @return
     */
    private String getOrInitDeviceId(HttpServletRequest request, HttpServletResponse response) {
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isNotBlank(deviceId) && !"null".equalsIgnoreCase(deviceId)) {
            return deviceId;
        }

        Cookie device = SessionUtil.findCookieByName(request, LoginService.USER_DEVICE_KEY);
        if (device == null) {
            deviceId = UUID.randomUUID().toString();
            if (response != null) {
                response.addCookie(SessionUtil.newCookie(LoginService.USER_DEVICE_KEY, deviceId));
            }
            return deviceId;
        }
        return device.getValue();
    }
}
