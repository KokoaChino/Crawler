package example;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;


public class UrlCrawl { // 抓取 URL 下的 HTML 代码以及图片

    private static final String url = "https://www.nipic.com/topic/show_29204_1.html"; // 要爬取的 URL

    public static void main(String[] args) {
        try {
            String info = apacheHttpClient();
            System.out.println("代码：\n" + info); // <----- 调试 ----->
            jsoup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void jsoup() throws Exception { // 抓取该 URL 下的图片
        Document document = Jsoup.connect(url).get(); // 连接到指定的 URL，并获取网页文档
        Elements select = document.select("li.new-search-works-item"); // 从文档中选择所有符合指定选择器的元素
        for (Element element: select) { // 遍历选中的元素
            Elements imgElement = element.select("a > img"); // 从元素中选择第一个子元素 a 的 img 标签
            String imgUrl = imgElement.attr("src"); // 获取 img 标签的 src 属性值
            if (imgUrl.startsWith("//")) { // 如果 imgUrl 以 "//" 开头，补全为完整的 URL
                imgUrl = "https:" + imgUrl; // 添加 https: 前缀
            }
            Connection.Response response = Jsoup.connect(imgUrl) // 连接到图片的 URL，设置用户代理，并忽略内容类型
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                    .ignoreContentType(true).execute(); // 执行 HTTP 请求
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(10); // 文件名
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(response.bodyAsBytes()),
                    new File("Output\\" + fileName + ".png")); // 生成图片文件
        }
    }

    private static String apacheHttpClient() throws Exception { // 获取页面的 HTML 代码
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // 创建一个 HTTP GET 请求
        httpGet.setHeader("user-agent", // 设置请求头中的用户代理信息
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
        try (CloseableHttpResponse httpResponse = (CloseableHttpResponse) httpClient.execute(httpGet)) { // 执行 HTTP 请求
            return EntityUtils.toString(httpResponse.getEntity()); // 获取响应实体
        }
    }
}