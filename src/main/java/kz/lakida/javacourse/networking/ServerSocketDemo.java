package kz.lakida.javacourse.networking;

import javax.naming.ldap.SortKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerSocketDemo implements Runnable {


    private final Socket socket;

    public static void main(String[] args)  {
        System.out.println("Server started");

        try (var serverSocket = new ServerSocket(14001)) {
            //Пока serverSocket не закрыт
            while (!serverSocket.isClosed()) {
                var socket = serverSocket.accept();
                //Передаю socket через конструктор
                (new Thread(new ServerSocketDemo(socket))).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Конструктор
    ServerSocketDemo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        // Клиент подключился, открываем потоки
        System.out.println("Got connection from " + socket.getInetAddress().toString());

        try (var inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           var outputStream = socket.getOutputStream()) {

                // Читаем данные от клиента
                var line = inputStream.readLine();
                System.out.println("Request from client: " + line);

                // Возвращаем ответ
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                // Закрываем



        } catch (Exception exception) {
            exception.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
