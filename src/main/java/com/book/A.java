package com.book;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class A { // 抓取千恋万花的壁纸

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://jufei66.com/?cases_90/#&gid=1&pid=1")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String html = response.body();
            Matcher matcher = Pattern.compile("src=\"(.*?jpg)\"").matcher(html);
            for (int i = 0; matcher.find(); i++) {
                HttpRequest imageRequest = HttpRequest.newBuilder().uri(new URI("http://jufei66.com" + matcher.group(1))).build();
                HttpResponse<InputStream> imageResponse = client.send(imageRequest, HttpResponse.BodyHandlers.ofInputStream());
                InputStream imageInput = imageResponse.body();
                FileOutputStream stream = new FileOutputStream(String.format("千恋万花 %d.jpg", i));
                try (stream; imageInput) {
                    int size;
                    byte[] data = new byte[1024];
                    while ((size = imageInput.read(data)) > 0) {
                        stream.write(data, 0, size);
                    }
                }
            }
        }
    }

}
