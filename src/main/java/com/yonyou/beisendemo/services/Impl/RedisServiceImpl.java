package com.yonyou.beisendemo.services.Impl;

import com.yonyou.beisendemo.services.RedisService;
import com.yonyou.beisendemo.utils.RedisClient;
import org.springframework.stereotype.Service;

@Service("RedisService")
public class RedisServiceImpl implements RedisService {

    @Override
    public void testRedis() {
        RedisClient.setStrWithExpire("test" , "test" , 2000L);
    }

}
