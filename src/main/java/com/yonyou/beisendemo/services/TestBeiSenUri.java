package com.yonyou.beisendemo.services;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class TestBeiSenUri {


    public static void main(String[] args) {
        String urls = "https://dfiles.tms.beisen.com/report2/107293/1698074702/4/1a2e9c6e_323c_4a20_9f6e_b9dbb84ea70e_efhyygv1czzj.pdf?sig_t=1722847339&sig_exp=2592000&sig_a=recruitment&sig_pm=8&sig_npm=2&sig_v=1&sig=e55f43ab9b5a3aa783856a6e1c4e1534fda02dff";
       try {
           OkHttpClient client = new OkHttpClient();

           Request request = new Request.Builder()
                   .url(urls)
                   .build();
           try (Response response = client.newCall(request).execute()) {
               if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

               // 获取响应体
               ResponseBody responseBody = response.body();
               if (responseBody == null) throw new IOException("No response body");

               // 创建文件输出流
               try (FileOutputStream fos = new FileOutputStream("223.pdf")) {
                   // 创建输入流来读取响应体
                   try (InputStream inputStream = responseBody.byteStream()) {
                       // 读取并写入文件
                       byte[] buffer = new byte[2048];
                       int len;
                       while ((len = inputStream.read(buffer)) != -1) {
                           fos.write(buffer, 0, len);
                       }
                   }
               }

               // 关闭响应体
               responseBody.close();
           }


//           URL url = new URL(urls);
//           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//           httpURLConnection.setRequestMethod("GET");
//
//           int responseCode = httpURLConnection.getResponseCode();
//
//           if (responseCode == HttpURLConnection.HTTP_OK) {
//               try (InputStream inputStream = httpURLConnection.getInputStream();
//                    FileOutputStream fileOutputStream = new FileOutputStream("TestName.pdf")) {
//
//                   int bufferSize = 4096;
//                   byte[] buffer = new byte[bufferSize];
//                   int bytesRead;
//
//                   while ((bytesRead = inputStream.read(buffer)) != -1) {
//                       fileOutputStream.write(buffer, 0, bytesRead);
//                   }
//               }
//
//               httpURLConnection.disconnect();
//           } else {
//               System.out.println("服务器返回了错误代码: " + responseCode);
//           }
       }catch (IOException ex){
           ex.printStackTrace();
       }
    }
}
