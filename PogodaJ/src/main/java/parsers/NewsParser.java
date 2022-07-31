package parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsParser extends AbstractParser {


    public NewsParser(String url) {
        super(url);
    }

    @Override
    public String getData() {
        Document page;
        try {
            page = getPage(url);
            Element dateCode = page.select("h2[class = news-listing__day-date]").first();
            Element listNews = page.select("ul[class = news-listing__day-list]").first();
            String date = dateCode.text();
            Elements headLines = listNews.select("li[class = news-listing__item]");

            result.append("Новости за ").append(date).append(":").append("\n");
            result.append("\n");

            for (int i = headLines.size() - 1; i >= 0; i--) {
                String time = headLines.get(i).select("span[class = news-listing__item-time]").text();
                String news = headLines.get(i).select("h3[class = news-listing__item-title]").text();
                result.append(time).append(" ").append(news).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }
}
