import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    public static void main(String[] args) {
        Parser parser = new Parser();

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
                     System.out.println("Request: " + request);
                     String response;
                     if (Objects.equals(request, "Погода")) {
                         response = Parser.weatherForecast();

                     } else {
                         response = "Запрос не обработан";
                     }
                     System.out.println("Response: " + response);
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
