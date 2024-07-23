package com.zyh.ZyhG1.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestHelper {
    public String post(String urlStr, String param) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();//创建连接
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10 * 60 * 1000);
            connection.setReadTimeout(10 * 60 * 1000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.write(param.getBytes(StandardCharsets.UTF_8));
            dos.flush();
            dos.close();//写完记得关闭
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 判断请求是否成功
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    arrayOutputStream.write(bytes, 0, length);
                    arrayOutputStream.flush();
                }//读取响应体的内容
                return arrayOutputStream.toString();//返回请求到的内容，字符串形式
            } else {
                return "-1";//如果请求失败返回-1
            }
        } catch (Exception e) {
            return "-1";//出现异常也返回-1
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String get(String urlStr) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    arrayOutputStream.write(bytes, 0, length);
                    arrayOutputStream.flush();//强制释放缓冲区
                }
                return arrayOutputStream.toString();
            } else {
                return "-1";
            }
        } catch (Exception e) {
            return "-1";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
