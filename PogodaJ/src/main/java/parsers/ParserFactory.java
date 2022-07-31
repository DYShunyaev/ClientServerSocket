package parsers;

import constants.Link;

public class ParserFactory {
    public static AbstractParser factoryMethod(String req) {
        switch (req) {
            case "Новости" -> {return new NewsParser(Link.NEWS_LINK);}
            case "Погода" ->  {return new WeatherParser(Link.WEATHER_LINK);}
            default -> throw new IllegalArgumentException("Incorrect request");
        }
    }
}
