package com.yonyou.beisendemo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringTest {

    static void getFile(){
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add("1");
        stringJoiner.add("2");
        System.out.println(stringJoiner);

        List<String> res = new ArrayList<>();
        res.add("1");
//        res.add("2");
        String resStr = res.stream().collect(Collectors.joining(","));
        System.out.println(resStr);
        String join = String.join(",", Arrays.asList(new String[res.size()]).stream().map(s -> "?").toArray(String[]::new));
        System.out.println(join);
    }

    public static void main(String[] args) {
        getFile();
    }
}
