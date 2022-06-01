package kz.lakida.javacourse.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSocketDemo implements Runnable {

    private final Socket socket;
    private final String name;

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int threadClient = 0;

        while (threadClient < 10) {
            threadClient++;
            executorService.execute(new ClientSocketDemo("Client " + threadClient));
        }
        executorService.shutdown();
    }

    ClientSocketDemo(String name) throws IOException, InterruptedException {
        this.name = name;
        this.socket = new Socket("127.0.0.1", 14001);
        System.out.println("Started connection");
    }

    @Override
    public void run() {
        try (var inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var outputStream = socket.getOutputStream();) {


            // Отправляем данные по сети
            outputStream.write(("Hello from " + name + "!\r\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            // Получаем ответ от сервера
            System.out.println("Response from server: " + inputStream.readLine());

            // Закрываем
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
