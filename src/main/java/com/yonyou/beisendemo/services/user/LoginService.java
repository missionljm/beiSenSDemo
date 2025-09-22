package com.yonyou.beisendemo.services.user;


public interface LoginService {

    String SESSION_KEY = "f-session";
    String USER_DEVICE_KEY = "f-device";

    /**
     * 用户名密码方式登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    String loginByUserPwd(String username, String password);
}
