package com.book;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;


public class D { // 批量下载图片

    private static final String[] urls = new String[]{}; // 图片链接
    private static final String prefix = "https://prod-alicdn-community.kurobbs.com/newHead/aki/";
    private static final String suffix = ".png";
    private static final Map<String, String> PinYin = Map.ofEntries(
            Map.entry("安可", "anke"),
            Map.entry("守岸人", "shouanren"),
            Map.entry("长离", "changli"),
            Map.entry("丹瑾", "danjing"),
            Map.entry("维里奈", "weilinai"),
            Map.entry("今汐", "jinxi"),
            Map.entry("凌阳", "lingyang"),
            Map.entry("卡卡罗", "kakaluo"),
            Map.entry("吟霖", "yinlin"),
            Map.entry("忌炎", "jiyan"),
            Map.entry("折枝", "zhezhi"),
            Map.entry("散华", "sanhua"),
            Map.entry("桃祈", "taoqi"),
            Map.entry("渊武", "yuanwu"),
            Map.entry("漂泊者 - 男", "piaobozhenan"),
            Map.entry("漂泊者 - 女", "piaobozhenv"),
            Map.entry("炽霞", "chixia"),
            Map.entry("白芷", "baizhi"),
            Map.entry("相里要", "xiangliyao"),
            Map.entry("秋水", "qiushui"),
            Map.entry("秧秧", "yangyang"),
            Map.entry("莫特斐", "motefei"),
            Map.entry("鉴心","jianxin")
    );

    public static String convertToPinyin(String name) {
        return PinYin.get(name);
    }

    public static void main(String[] args) {
        for (String name : PinYin.keySet()) {
            String url = prefix + convertToPinyin(name) + suffix;
            download(url, name);
        }
    }

    private static void download(String url, String name) { // 下载图片
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
                return;
            }
            InputStream inputStream = connection.getInputStream(); // 获取输入流
            String fileName = name + ".png";
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
        }
    }
}
