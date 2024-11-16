package com.book;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class C { // 批量下载视频

    private static final String[] urls = new String[]{ // 视频链接
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-09-27/56739045/57ed9c5ef3a42e5566ece2200d83d035_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-09-27/100328174/f0b222616a2601905c2f88e00cb4f293_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-09-21/103578915/9ab241baafdbf3b55563d27830c5181e_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-02-21/18419305/a4fa861cc121371eb4ffd1cefdc00ce7_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-06-29/81961146/8d1473588ecc4001a4bba342d1e65126_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-02-18/21003138/ab53be72eba18e6bfbc08eafb6071890_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-01-30/38981360/a09b17d7fc38eb47a449d78600dffcfc_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2024-09-28/103578915/7895e2e5d5f674fcdcd30e64f8db255e_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-12-22/83952443/f7f3a7ba27975f2952fd15db030a3eba_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-12-29/80592846/343ee6b7a0d4434f0293c842dc36fc5c_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-12-30/102182501/af3b30b9aa37a14d795334371882d989_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-12-26/16630188/2b7676d23893404fa47be5a9950ba6e8_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-12-06/7002343/911fe1ffadefe87b574b37b601afcfae_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-11-27/89099565/50f5f71384e78932df25aa70ff97a9a1_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-11-09/11646741/43e3bbedd6617b89ef3101857409d1af_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-10-24/95084944/ada6949347bc9b7343afd479b2118a62_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-09-19/98558254/7b93166de9878d43dd3bf90163fa5421_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-09-17/14936238/d690f511988efd8a0a5ddce6957ef662_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-08-30/9007332/f18bfe017971ad9d01f2516fcc71e68e_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-12-09/93618663/9b881f72d6b54ab4f8866550de9c0f02_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-08-26/24924716/a841805035e9454e6e00984562efe46e_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-08-30/9007332/f18bfe017971ad9d01f2516fcc71e68e_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-08-23/11529059/6a6ef2cef93f8568b6995a98698ca163_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-08-14/9447227/b3e1b146fa3ea853f953b33ef95d07dd_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-12-09/93618663/9b881f72d6b54ab4f8866550de9c0f02_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-07-26/57813917/15514b1c0debb056a76c1b170c3b2607_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-07-26/57813917/c390d4245017c457da27549d563819c9_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-11-25/98732970/05b685e46d951f57f6b4dc38e05557ac_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2023-05-08/100750058/38e6c0316d8f1b7dcb7a2417eaa81d69_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-11-28/93618663/4e7600b841504bcad765c98caadb2790_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2021-10-16/88839405/f2f785d4bd8fc2ed8c1441ff7aad006a_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2021-10-21/88839405/6fc671ba059c9b60d2fe3e196a981d1d_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-08-01/97048655/2f6680adf54f83a20662f26ac6da82e8_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-08-01/74783262/a7dcd8b5a233dd0558386fd9e2513d21_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-07-07/8155929/f4cf1eb154c08209619a9857a8fb93a5_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-06-17/96069275/a337bb2f75ad29449ebded202d887b99_wm.mp4",
            "https://uifbjasb221dc.xfzmtx9.xyz/uploads/2022-06-16/54760352/404fd228bb034d8cf98a3b4805b7bf20_wm.mp4"
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
        }
    }
}
