package com.yonyou.beisendemo.services.Impl;

import com.yonyou.beisendemo.services.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceImplTest {

    @Autowired
    private RedisService redisService;

    @Test
    void testRedis() {
        redisService.testRedis();
    }

}