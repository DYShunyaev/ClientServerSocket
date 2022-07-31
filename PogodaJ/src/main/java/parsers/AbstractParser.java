package parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractParser {
    StringBuilder result = new StringBuilder();
    String url;

    public AbstractParser(String url) {
        this.url = url;
    }

    Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 3000);
    }

    public abstract String getData();

}
