package com.yonyou.beisendemo.services;

import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.yonyou.beisendemo.entity.FIleTest;
import com.yonyou.beisendemo.entity.FileTest2;

import java.util.List;

public class FileTest {

    public static void main(String[] args) {
        // 读取 Excel 文件
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\missionljm\\IdeaProjects\\beiSenDemo\\测试测试.xlsx");

        // 读取第一个 Sheet 的数据，从第五行开始
        reader.setSheet(0); // 切换到第一个 Sheet
        List<FIleTest> sheet1Data = reader.read(0, -1, FIleTest.class); // 从第五行开始读取
        System.out.println("Sheet1 数据: " + JSONUtil.toJsonStr(sheet1Data));

        // 读取第二个 Sheet 的数据，从第五行开始
        reader.setSheet(1); // 切换到第二个 Sheet

        List<FileTest2> sheet2Data = reader.read(0, 3, FileTest2.class); // 从第五行开始读取
        System.out.println("Sheet2 数据: " + JSONUtil.toJsonStr(sheet2Data));

        // 关闭读取器
        reader.close();
    }
}
