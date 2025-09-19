package com.yonyou.beisendemo.services;

import cn.hutool.core.date.StopWatch;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.Optional;


public interface TestFIleSec {


    void uploadFile(String args);

//    public void ATest(){
//        {
//            List<CooperationFileInfo> cooperationFileInfos = cooperationFileService.queryFileList("c-fcc-i-at-project", id, 1, 50);
//            String ossFileUploadUrl = domainUrl + "/iuap-api-gateway/yonbip/znbz/rbsm/api/attach/upload?access_token=" + accessToken.getAccessToken();
//            log.error("附件方法:cooperationFileInfos==>"+JSONObject.toJSONString(cooperationFileInfos));
//            for (CooperationFileInfo fileInfo : cooperationFileInfos) {
//                log.error("附件名称：{} , 附件大小：{} ",fileInfo.getName(),fileInfo.getSize());
//                InputStream inputStream = null;
//                ByteArrayOutputStream buffer = null;
//                try {
//                    inputStream = getInputStream(fileInfo.getBucketUrl());
//                    log.error("{}==>inputStream:{} ",fileInfo.getName(),inputStream.toString());
//                    // 修改部分：使用缓冲方式读取文件
//                    buffer = new ByteArrayOutputStream();
//                    int nRead;
//                    byte[] b = new byte[8192]; // 8KB缓冲区
//                    while ((nRead = inputStream.read(b, 0, b.length)) != -1) {
//                        buffer.write(b, 0, nRead);
//                    }
//                    buffer.flush();
//                    byte[] bytes = buffer.toByteArray();
//                    log.error("{}==>bytes:{} ",fileInfo.getName(),Base64Util.encode(bytes));
//                    JSONObject param = new JSONObject();
//                    param.put("billType", "znbzbx_expensebill");
//                    param.put("fileName", fileInfo.getName());
//                    param.put("file", Base64Util.encode(bytes));
//                    param.put("attachmentAss", fileId);
//                    log.error("{}param:{} ", fileInfo.getName(), param.toJSONString());
//                    String uploadResult = HttpUtil.post(ossFileUploadUrl, param.toJSONString());
//                    JSONObject jsonObject = JSONObject.parseObject(uploadResult);
//                    log.error("上传附件结果：{}", jsonObject);
//                } catch (IOException e) {
//                    log.error("读取文件流异常: " + fileInfo.getName(), e);
//                } finally {
//                    // 确保资源正确关闭
//                    if (inputStream != null) {
//                        try {
//                            inputStream.close();
//                        } catch (IOException e) {
//                            log.error("关闭inputStream异常", e);
//                        }
//                    }
//                    if (buffer != null) {
//                        try {
//                            buffer.close();
//                        } catch (IOException e) {
//                            log.error("关闭buffer异常", e);
//                        }
//                    }
//                }
//            }
//        }
//    }
}
