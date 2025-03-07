package com.book;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class C { // 批量下载视频

    private static final String[] urls = new String[]{ // 视频链接

    };

    public static void main(String[] args) {
        for (String url : urls) {
            download(url);
        }
    }

    private static void download(String url) { // 下载视频
        try {
            URL videoUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) videoUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 设置连接超时
            connection.setReadTimeout(10000); // 设置读取超时
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode(); // 获取响应代码，确保连接正常
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("\t下载失败，HTTP 响应码：" + responseCode);
                System.out.println(url);
                return;
            }
            InputStream inputStream = connection.getInputStream(); // 获取输入流
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(3) + ".mp4";
            File outputFile = new File("Output", fileName);
            long fileSize = connection.getContentLength(), totalBytesRead = 0;
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) { // 保存文件
                byte[] buffer = new byte[4096]; // 设置缓冲区大小
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    long progress = (totalBytesRead * 100) / fileSize;
                    System.out.print("\r下载进度: " + progress + "%");
                }
            }
            inputStream.close();
            System.out.println("\t下载成功：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\t下载失败：" + e.getMessage());
            System.out.println(url);
        }
    }
}
