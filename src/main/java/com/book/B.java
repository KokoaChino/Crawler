package com.book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class B { // 抓取星灵感应官网壁纸

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            for (int i = 1; i <= 12; i++) {
                File file = new File("Output\\" + i);
                if (!file.exists()) file.mkdirs();
                for (int j = 1; j <= 6; j++) {
                    HttpRequest imageRequest = HttpRequest.newBuilder().uri(new URI("https://hoshitele-anime.com/dist/img/story/ep" + i + "/img0" + j +".jpg?ver=2.086")).build();
                    HttpResponse<InputStream> imageResponse = client.send(imageRequest, HttpResponse.BodyHandlers.ofInputStream());
                    InputStream imageInput = imageResponse.body();
                    FileOutputStream stream = new FileOutputStream(file.getPath() + "\\" + j + ".webp");
                    try (stream; imageInput) {
                        int size;
                        byte[] data = new byte[2048];
                        while ((size = imageInput.read(data)) > 0) {
                            stream.write(data, 0, size);
                        }
                    }
                }
            }
        }
    }

}