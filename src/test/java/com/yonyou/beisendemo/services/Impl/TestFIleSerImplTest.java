package com.yonyou.beisendemo.services.Impl;

import com.yonyou.beisendemo.services.TestFIleSec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestFIleSerImplTest {

    @Autowired
    private TestFIleSec testFIleSerImpl;

    @Test
    void testRedis() {
        testFIleSerImpl.uploadFile("111");
    }

}