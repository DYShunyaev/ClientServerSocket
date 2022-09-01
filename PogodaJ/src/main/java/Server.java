import Parsers.CurrentDate;
import Parsers.Horoscope;
import Parsers.News;
import Parsers.Parser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000))
         {
             System.out.println("Server started!");

             while (true)
                 try (
                        Socket socket = server.accept();
                        BufferedWriter writer =
                            new BufferedWriter(
                                 new OutputStreamWriter(
                                         socket.getOutputStream()));
                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()))
                 ) {
                     String request = reader.readLine();
                     System.out.println("Запрос: " + request);
                     String response;
                     if (Objects.equals(request, "Погода")) {
                         response = Parser.weatherForecast();

                     } else if (Objects.equals(request, "Новости")) {
                         response = News.getNews();
                     } else if (Objects.equals(request, "Точное время")) {
                         response = String.valueOf(CurrentDate.getDate());
                     } else if (Objects.equals(request, "Гороскоп")) {
                         System.out.print("Введите знак зодиака: ");
                         String horoscope = reader.readLine();
                         response = Horoscope.getHoroscope(horoscope);
                         Horoscope.clearSB();
                     } else if (Objects.equals(request,"Стоп")) {
                         break;
                     } else {
                         response = "\"Запрос не обработан\"";
                     }
                     System.out.println("Ответ: " + response);
                     writer.write(Objects.requireNonNull(response));
                     writer.newLine();
                     writer.flush();
                 } catch (NullPointerException e){
                        e.printStackTrace();
                 }

        } catch (Exception e){
            throw new RuntimeException(e);
        }
     }
}
