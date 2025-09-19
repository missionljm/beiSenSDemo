package com.yonyou.beisendemo.services;

import java.util.HashMap;
import java.util.Map;

public class SimpleWubiConverter {

    private static final Map<Character, String> WUBI_MAP = new HashMap<>();

    static {
        // 这里只添加部分示例，实际应用需要完整的五笔字典
        WUBI_MAP.put('汉', "ic");
        WUBI_MAP.put('字', "pb");
        WUBI_MAP.put('转', "lfn");
        WUBI_MAP.put('换', "rq");
        // 需要补充完整的汉字五笔映射
    }

    public static String hanziToWubi(String hanzi) {
        StringBuilder result = new StringBuilder();
        for (char c : hanzi.toCharArray()) {
            result.append(WUBI_MAP.getOrDefault(c, String.valueOf(c))).append(" ");
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        String text = "汉字转换";
        System.out.println("五笔: " + hanziToWubi(text));
        // 输出: 五笔: ic pb lfn rq
    }
}
