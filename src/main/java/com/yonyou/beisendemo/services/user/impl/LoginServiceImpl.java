package com.yonyou.beisendemo.services.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yonyou.beisendemo.context.ReqInfoContext;
import com.yonyou.beisendemo.dao.user.UserDao;
import com.yonyou.beisendemo.entity.user.UserDO;
import com.yonyou.beisendemo.exception.ExceptionUtil;
import com.yonyou.beisendemo.services.help.UserPwdEncoder;
import com.yonyou.beisendemo.services.help.UserSessionHelper;
import com.yonyou.beisendemo.services.user.LoginService;
import com.yonyou.beisendemo.vo.constants.StatusEnum;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPwdEncoder userPwdEncoder;

    @Autowired
    private UserSessionHelper userSessionHelper;

    @Override
    public String loginByUserPwd(String username, String password) {
        UserDO user = userDao.getUserByUserName(username);
        if (ObjectUtil.isEmpty(user)){
            throw ExceptionUtil.of(StatusEnum.USER_EXISTS , "username" +  username);
        }
        if (!userPwdEncoder.match(password , user.getPassword())){
            throw ExceptionUtil.of(StatusEnum.USER_PWD_ERROR);
        }
        Long userId = user.getId();
        // 登录成功，返回对应的session
        ReqInfoContext.getReqInfo().setUserId(userId);
        return userSessionHelper.genSession(userId);
    }
}
