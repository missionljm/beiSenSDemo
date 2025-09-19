package com.yonyou.beisendemo.services;

import cn.hutool.core.map.MapUtil;
import com.yonyou.beisendemo.entity.TestEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestFirst {

    public static void main(String[] args) {
//        Map hm = new HashMap();
//        hm.put("1", "1");
//        hm.put("2", "2");
//        hm.put("3", "3");
//        hm.put("4", "4");
//        Map st = MapUtil.getAny(hm , "4");
//        System.out.println(MapUtil.getAny(hm , "4"));
        String str = "+86-17673841314";
        System.out.println(str.substring(str.length()-11));


        TestEntity testEntity = new TestEntity();
        TestEntity testEntity2 = new TestEntity();
        TestEntity testEntity3= new TestEntity();
        testEntity.setId("111111");
        testEntity2.setId("222222");
        testEntity3.setId("333333");
        List<TestEntity> addList = new ArrayList<>();
        addList.add(testEntity);
        addList.add(testEntity2);
        addList.add(testEntity3);
        String sql = addList.stream().map(TestEntity::getId).collect(Collectors.joining(","));
        System.out.println(sql);

        List<String> a = new ArrayList<>();
        a.add("1954681254792462456");
        a.add("1954681254792462457");
        a.add("1954681254792462459");
        a.add("1954681254792462457");
        Object c = "1954681254792462457";
        System.out.println(a.contains(c));
        System.out.println(a.contains(c.toString()));
    }
}
