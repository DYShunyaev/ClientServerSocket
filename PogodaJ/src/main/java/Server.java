import parsers.AbstractParser;
import parsers.NewsParser;
import parsers.ParserFactory;
import parsers.WeatherParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8000)) {
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
                    System.out.println("Request: " + request);
                    AbstractParser parser = ParserFactory.factoryMethod(request);

                    String response = parser.getData();
                    System.out.println("Response: " + response);
                    writer.write(Objects.requireNonNull(response));
                    writer.newLine();
                    writer.flush();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
