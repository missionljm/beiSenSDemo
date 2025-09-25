package com.yonyou.beisendemo;

import com.yonyou.beisendemo.global.ForumExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@ServletComponentScan
//@MapperScan({
//        "com.yonyou.beisendemo.mapper.user"
//})
public class BeiSenDemoApplication implements WebMvcConfigurer , ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BeiSenDemoApplication.class, args);
    }


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new ForumExceptionHandler());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
    