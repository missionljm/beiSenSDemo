package com.yonyou.beisendemo.services.user.impl;

import com.yonyou.beisendemo.dao.user.UserDao;
import com.yonyou.beisendemo.dto.user.BaseUserInfoDTO;
import com.yonyou.beisendemo.entity.IpInfo;
import com.yonyou.beisendemo.entity.user.UserInfoDO;
import com.yonyou.beisendemo.services.help.UserSessionHelper;
import com.yonyou.beisendemo.services.user.UserService;
import com.yonyou.beisendemo.services.user.converter.UserConverter;
import com.yonyou.beisendemo.utils.IpUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户Service
 *
 * @author louzai
 * @date 2022-07-20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserSessionHelper userSessionHelper;

    @Resource
    private UserDao userDao;

    @Override
    public BaseUserInfoDTO getAndUpdateUserIpInfoBySessionId(String session, String clientIp) {
        if (StringUtils.isBlank(session)) {
            return null;
        }

        Long userId = userSessionHelper.getUserIdBySession(session);
        if (userId == null) {
            return null;
        }

        // 查询用户信息，并更新最后一次使用的ip
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            // 常见于：session中记录的用户被删除了，直接移除缓存中的session，走重新登录流程
            userSessionHelper.removeSession(session);
            return null;
        }

        var ip = user.getIp();
        if (clientIp != null && !Objects.equals(ip.getLatestIp(), clientIp)) {
            // ip不同，需要更新
            ip.setLatestIp(clientIp);
            ip.setLatestRegion(IpUtil.getLocationByIp(clientIp).toRegionStr());

            if (ip.getFirstIp() == null) {
                ip.setFirstIp(clientIp);
                ip.setFirstRegion(ip.getLatestRegion());
            }
            userDao.updateById(user);
        }

        // 查询 user_ai信息，标注用户是否为星球专属用户
//        UserAiDO userAiDO = userAiDao.getByUserId(userId);
//        this.autoUpdateUserStarState(userAiDO);

        return UserConverter.toDTO(user);
    }
}
