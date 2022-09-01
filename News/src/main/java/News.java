import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class News {

    public static final StringBuilder RESULT = new StringBuilder();

    private static Document getPage() throws IOException {
        String url = "https://spb.mk.ru/news/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
    public static void main(String[] args) throws IOException {
        Document page = getPage();
        Element dateCode = page.select("h2[class = news-listing__day-date]").first();
        Element listNews = page.select("ul[class = news-listing__day-list]").first();
        String date = dateCode.text();
        Elements headLines = listNews.select("li[class = news-listing__item]");
        RESULT.append("Новости за ").append(date).append(":").append("\n");
        RESULT.append("\n");
        ArrayList<String> list = new ArrayList<>();
        for (Element strings : headLines) {
            String time = strings.select("span[class = news-listing__item-time]").text();
            String news = strings.select("h3[class = news-listing__item-title]").text();
            //RESULT.append(time).append(" ").append(news).append("\n");
            String text = time + " " + news;
            list.add(text);
        }
        for (int i = list.size(); i > 0; i--) {
            list.add(list.size() -i-1,list.get(i));
        }
        for (String strings : list) {
            System.out.println(list);
        }
    }
}
