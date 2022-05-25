package kz.lakida.javacourse.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class ServerSocketDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Открываем порт и ждем подключения
        System.out.println("Server started");
        var serverSocket = new ServerSocket(14001);
        var socket = serverSocket.accept();

        // Клиент подключился, открываем потоки
        System.out.println("Got connection from " + socket.getInetAddress().toString());
        var inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var outputStream = socket.getOutputStream();

        // Читаем данные от клиента
        var line = inputStream.readLine();
        System.out.println("Request from client: " + line);

        // Возвращаем ответ
        outputStream.write(line.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        // Закрываем
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

}
