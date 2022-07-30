import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.0.1", 8000);
                BufferedWriter writer =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream()));
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))
        )
        {
            System.out.println("Connected to server.");
            String request = "Санкт-Петербург";
            System.out.println("Request: " + request);

            writer.write(request);
            writer.newLine();
            writer.flush();

            List<String> response = reader.lines().collect(Collectors.toList());
            System.out.println("Response: ");
            response.forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
