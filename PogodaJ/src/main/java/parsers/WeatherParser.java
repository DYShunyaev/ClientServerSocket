package parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherParser extends AbstractParser {
    private final Pattern PATTERN = Pattern.compile("\\d{2}\\.\\d{2}");
    private final String HEADER = "\tЯвления\tТемпер.\tДавл.\tВлажность\tВетер\n";

    public WeatherParser(String url) {
        super(url);
    }

    private String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = PATTERN.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private int printPartValues(Elements values, int index) {
        int iterationCount = 4;
        if (index == 0) {
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            boolean isDay = valueLn.text().contains("День");
            boolean isEvening = valueLn.text().contains("Вечер");
            boolean isNight = valueLn.text().contains("Ночь");
            if (isMorning) {
                iterationCount = 3;
            } else if (isDay) {
                iterationCount = 2;
            } else if (isEvening) {
                iterationCount = 1;
            } else if (isNight) {
                iterationCount = 0;
            }
        }
        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")) {
                result.append(td.text()).append('\t');
            }
            result.append("\n");
            System.out.println();
        }
        return iterationCount;
    }

    @Override
    public String getData() {
        Document page;
        try {
            page = getPage(url);
            Element tableWth = page.select("table[class=wt]").first();
            Elements names = tableWth.select("tr[class=wth]");
            Elements values = tableWth.select("tr[valign=top]");
            int index = 0;

            for (Element name : names) {
                String dateString = name.select("th[id=dt]").text();
                String date = getDateFromString(dateString);
                result.append("\n").append(date).append(HEADER);
                int iterationCount = printPartValues(values, index);
                index = index + iterationCount;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }
}
