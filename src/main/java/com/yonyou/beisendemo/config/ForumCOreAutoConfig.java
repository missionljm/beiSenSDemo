package com.yonyou.beisendemo.config;

import com.yonyou.beisendemo.utils.RedisClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ComponentScan("com.yonyou.beisendemo")
public class ForumCOreAutoConfig {


    public ForumCOreAutoConfig(RedisTemplate<String , String> redisTemplate) {
        RedisClient.register(redisTemplate);
    }
}
