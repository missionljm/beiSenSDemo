package com.yonyou.beisendemo.controller.login;

import com.yonyou.beisendemo.services.user.LoginService;
import com.yonyou.beisendemo.utils.SessionUtil;
import com.yonyou.beisendemo.vo.ResVo;
import com.yonyou.beisendemo.vo.constants.StatusEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-login/api")
public class LoginTestController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户名和密码登录
     * 可以根据星球编号/用户名进行密码匹配
     */
    @GetMapping("/login/username")
    public ResVo<Boolean> login(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password,
                                HttpServletResponse response) {
        String session = loginService.loginByUserPwd(username, password);
        if (StringUtils.isNotBlank(session)) {
            // cookie中写入用户登录信息，用于身份识别
            response.addCookie(SessionUtil.newCookie(LoginService.SESSION_KEY, session));
            return ResVo.ok(true);
        } else {
            return ResVo.fail(StatusEnum.LOGIN_FAILED_MIXED, "用户名和密码登录异常，请稍后重试");
        }
    }

}
