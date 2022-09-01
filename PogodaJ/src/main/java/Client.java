import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        System.out.print("""
                        Если хотите узнать Погоду - введите "Погода"
                        Если хотите узнать Новости - введите "Новости"
                        Если хотите узнать Точное время и дату - введите "Точное время"
                        Если хотите узнать свой Гороскоп - введите "Гороскоп"
                        Если хотите выйти - введите "Стоп" \s""");

        while (true) {
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
            ) {
                System.out.print("\nConnected to server.\n");
                System.out.print("\nВведите запрос: ");

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
                String request = reader1.readLine();
                System.out.println("Запрос: " + request);

                writer.write(request);
                writer.newLine();
                writer.flush();

                if (request.equals("Гороскоп")) {

                    System.out.print("Введите знак зодиака: ");
                    String horoscope = reader1.readLine();

                    writer.write(horoscope);
                    writer.newLine();
                    writer.flush();

                } else if (request.equals("Стоп")) {
                    break;
                }

                List<String> response = reader.lines().toList();
                System.out.println("Ответ: ");
                response.forEach(System.out::println);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
