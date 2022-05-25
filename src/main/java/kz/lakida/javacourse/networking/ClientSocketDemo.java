package kz.lakida.javacourse.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSocketDemo {

    public static void main(String[] args) throws IOException {
        // Инициализация подключения и настройка потоков
        var socket = new Socket("127.0.0.1", 14001);
        System.out.println("Started connection");
        var inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var outputStream = socket.getOutputStream();

        // Отправляем данные по сети
        outputStream.write("Hello, world!\r\n".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        // Получаем ответ от сервера
        System.out.println("Response from server: " + inputStream.readLine());

        // Закрываем
        inputStream.close();
        outputStream.close();
        socket.close();
    }

}
