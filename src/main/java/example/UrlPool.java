package example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UrlPool { // 抓取基本 URL 下的所有 URL

    private static final Pattern aPattern = Pattern.compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>"); // 匹配网页中的链接
    private static final Set<String> links = new HashSet<>(); // 抓取到的链接
    private static String mainHost; // 主机部分

    public static void main(String[] args) {
        getUrl("https://www.nipic.com/"); // 传入基本 URL，抓取该页面下所有的 URL
        System.out.println("链接集合：\n" + links); // <----- 调试 ----->
    }

    private static void getUrl(String baseUrl) {
        Pattern mainHostPattern = Pattern.compile("(https?://)?[^/\\s]*"); // 匹配 URL 的主机部分：www.nipic.com
        Matcher matcher = mainHostPattern.matcher(baseUrl);
        if (matcher.find()) {
            mainHost = matcher.group(); // 初始化主机部分
        }
        try {
            crawlerLinks(baseUrl); // DFS 抓取
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crawlerLinks(String link) throws Exception { // 记忆化搜索
        if (links.contains(link)) return; // 已经抓取过
        links.add(link); // 记录该链接
        System.out.println("链接：" + link); // <----- 调试 ----->
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(link).openConnection(); // 打开连接
        httpURLConnection.setRequestMethod("GET"); // 设置请求方法为 GET
        if (httpURLConnection.getResponseCode() == 200) { // 响应码为 200
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())); // 读取响应内容
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = aPattern.matcher(line);
                if (matcher.find()) {
                    String newLink = getNewLink(matcher); // 获取新链接
                    if (newLink.contains(mainHost)) {
                        crawlerLinks(newLink);
                    }
                }
            }
        }
    }

    private static String getNewLink(Matcher matcher) { // 获取新链接
        String newLink = matcher.group(1).trim(); // 获取链接并去除前后空格
        if (!newLink.startsWith("http")) { // 拼接链接
            if (newLink.startsWith("/")) {
                newLink = mainHost + newLink;
            } else {
                newLink = mainHost + "/" + newLink;
            }
        }
        if (newLink.endsWith("/")) {
            newLink = newLink.substring(0, newLink.length() - 1);
        }
        return newLink;
    }
}