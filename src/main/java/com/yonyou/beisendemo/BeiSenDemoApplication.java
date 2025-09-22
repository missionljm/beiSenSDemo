package com.yonyou.beisendemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan({
//        "com.yonyou.beisendemo.mapper.user"
//})
public class BeiSenDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeiSenDemoApplication.class, args);
    }


}
    