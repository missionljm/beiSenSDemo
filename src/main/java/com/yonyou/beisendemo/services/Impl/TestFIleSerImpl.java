package com.yonyou.beisendemo.services.Impl;

import cn.hutool.core.date.StopWatch;
import com.yonyou.beisendemo.services.TestFIleSec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service("TestFIleSec")
public class TestFIleSerImpl implements TestFIleSec {

    private static final Logger log = LoggerFactory.getLogger(TestFIleSec.class);

    @Override
    public void uploadFile(String args) {
        String urls = "https://pssc-sit.gac.com.cn/yms-def-bucket/iuap-apcom-file-private/iuap-apcom-file/me25db95/2025091009/3451c2e8-961c-4c08-bbdb-a19f3a5ccde8.pdf";
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            URL fileUrl = new URL(urls);
            InputStream inStream = fileUrl.openStream();
            byte[] bytes =  cn.hutool.core.io.IoUtil.readBytes(inStream);
            stopWatch.stop();
            log.info("耗时：{}", Optional.of(stopWatch.getTotalTimeMillis()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File("耗时.pdf");

        OkHttpClient client = new OkHttpClient();
//        StopWatch stopWatch = new StopWatch();


        Request request = new Request.Builder()
                .url(urls)
                .build();
        try {
            stopWatch.start();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // 获取响应体
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new IOException("No response body");

                // 创建文件输出流
                try (InputStream inputStream = responseBody.byteStream();
                     FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

                    ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
                    ByteBuffer buffer = ByteBuffer.allocate(128 * 1024);

                    while (inputChannel.read(buffer) != -1) {
                        buffer.flip();
                        fileChannel.write(buffer);
                        buffer.compact();
                    }
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        fileChannel.write(buffer);
                    }
                }

                // 关闭响应体
                responseBody.close();
            }
            stopWatch.stop();
            log.info("耗时：{}", Optional.of(stopWatch.getTotalTimeMillis()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
