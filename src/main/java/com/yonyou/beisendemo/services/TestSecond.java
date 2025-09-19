package com.yonyou.beisendemo.services;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yonyou.beisendemo.entity.FIleTest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.mock.web.MockMultipartFile;

public class TestSecond {

    public static void main(String[] args) {
//        List a = new ArrayList();
//        a.add("1");
//        a.add("2");
//        a.add("3");
//        a.add("4");
//        List b = new ArrayList();
//        b.addAll(a);
//        b.set(1 , "10");
//        b.remove(2);
//        a.remove(1);
//        System.out.println(JSONUtil.toJsonStr(a));
//        System.out.println(JSONUtil.toJsonStr(b));

//        String dataStr = "2017-03-29 22:33:23";
//        Date date = DateUtil.parse(dataStr);
//        String fu = "-5";
//        System.out.println(JSONUtil.toJsonStr(MapUtil.of("key" , "111")));
//        System.out.println(DateUtil.offsetDay(date , Integer.valueOf(fu)));
//        String sql = "123456789";
//        System.out.println(StrUtil.sub( sql, sql.length() - 5,sql.length()));
//        String dateStr2 = "202409";
//        boolean testDate = DateUtil.isSameMonth(DateUtil.parse(dateStr2 , "yyyyMM") , DateUtil.date());
//        String dateStr3 = DateUtil.format(DateUtil.date(), "yyyyMMdd");
//        System.out.println(dateStr3);
        ;
//        int i = -1;
//        System.out.println(10 + i);
//        System.out.println(DateUtil.beginOfMonth(new Date()));
//        System.out.println(DateUtil.endOfMonth(new Date()));
//
//        String date ="2019-09-09";
//        String date2 = DateUtil.format(DateUtil.parse(date) , "yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        sdf.setTimeZone(TimeZone.getTimeZone("CDT"));
//        Date data1 = null;
//        try {
//            data1 = sdf.parse(date2);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(new Date());
//        System.out.println(data1);
//
//        ExcelWriter writer = ExcelUtil.getWriter("" , "测试文件.xls");
//        writer.setSheet("主表");
//        writer.addHeaderAlias("id" , "id");
//        writer.addHeaderAlias("name" , "姓名");
//        writer.setSheet("子表");
//        writer.addHeaderAlias("id" , "id");
//        writer.addHeaderAlias("name" , "姓名");
//
//        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
//        writer.setOnlyAlias(true);
//
//// 合并单元格后的标题行，使用默认标题样式
//        writer.merge(4, "一班成绩单");
//// 一次性写出内容，使用默认样式，强制输出标题
//// 关闭writer，释放内存
//        writer.close();
//        List<FIleTest> files = new ArrayList<>();
//        FIleTest fileTest = new FIleTest();
//        fileTest.setFile1("1");
//        files.add(fileTest);
//        if (ObjectUtil.isEmpty(files.get(0).getFile2()) || files.get(0).getFile2().equals("10041001")){
//            System.out.println("1111111111");
//        }else {
//            System.out.println("22222222222");
//        }
//        String a = "1";
//        System.out.println(StrFormatter.format("维修工单号：{} 所对应工单不包含可报装的充电桩零件，不能报装",a));

//        String url = "https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Fimg.soogif.com%2FXMpNQPHjSPNtgdTyvyzXS0PUGxCQoQfr.gif&thumburl=https%3A%2F%2Fimg0.baidu.com%2Fit%2Fu%3D4265907615%2C1261343486%26fm%3D253%26fmt%3Dauto%26app%3D138%26f%3DGIF%3Fw%3D640%26h%3D360";
////        System.out.println(getFilenameFromUrl(url));
//        try {
//            System.out.println(getFilenameFromUrl(url) + "." + getFileFlex(url)) ;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("rGt" + generate(15));
        System.out.println(DateUtil.current());

    }

    private static String ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static String generate(int length) {

        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, ALLOWED_CHARS.length())
                .mapToObj(i -> String.valueOf(ALLOWED_CHARS.charAt(i))).collect(Collectors.joining());
    }

    private static String getFilenameFromUrl(String url) {
        try {
            // 尝试从URL路径中提取文件名
            String path = new java.net.URL(url).getPath();
            int lastSlash = path.lastIndexOf('/');
            if (lastSlash != -1 && lastSlash < path.length() - 1) {
                return path.substring(lastSlash + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    private static String getFilenameFromBs(String bs) {
        try {
            // 尝试从URL路径中提取文件名
            int lastSlash = bs.lastIndexOf('/');
            if (lastSlash != -1 && lastSlash < bs.length() - 1) {
                return bs.substring(lastSlash + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
    public static MultipartFile convertUrlToMultipartFile(String fileUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(fileUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download file: " + response);
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Response body is null");
            }

            byte[] bytes = body.bytes();
            String contentType = body.contentType() != null ? body.contentType().toString() : "application/octet-stream";

            // 从URL中提取文件名（如果可能）
            String filename = getFilenameFromUrl(fileUrl);
            return new MockMultipartFile(
                    "file",                      // 表单字段名称（可自定义）
                    StrUtil.isEmpty(filename) ? "file":filename,                    // 原始文件名
                    contentType,                        // 内容类型（可自动推断）
                    new ByteArrayInputStream(bytes) // 输入流
            );
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileFlex(String fileUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(fileUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download file: " + response);
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Response body is null");
            }

            byte[] bytes = body.bytes();
            String contentType = body.contentType() != null ? body.contentType().toString() : "application/octet-stream";
            return getFilenameFromBs(contentType);
        }catch (java.io.IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
