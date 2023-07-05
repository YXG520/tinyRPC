package com.yxg.protocol;

import com.yxg.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

    public String send(String hostname, Integer port, Invocation invocation) throws IOException {
        // 用户的配置

        try {

            URL url = new URL("http", hostname, port, "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            System.out.println("准备发送");
            // 配置
            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            // 发出去
            oos.writeObject(invocation);
            oos.flush();
            oos.close();
            System.out.println("获取响应结果");
            // 获取响应结果
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = IOUtils.toString(inputStream);
            System.out.println("返回用户之前的打印："+result);
            return result;
        } catch (MalformedURLException e) {

        } catch (IOException e) {
            throw e;

        }

        return null;
    }
}
