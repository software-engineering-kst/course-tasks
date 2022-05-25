package kz.lakida.javacourse.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpCallDemo {

    public static void main(String[] args) throws IOException {
        // DNS - Domain Name Service
        var socket = new Socket("www.fullerton.edu", 80);
        System.out.println("Connection established");
        var inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var outputStream = socket.getOutputStream();

        outputStream.write(("""
                GET / HTTP/1.1
                Host: www.fullerton.edu
                Accept: */*
                Connection: close
                
                """).getBytes(StandardCharsets.UTF_8));

        String line;
        while ((line = inputStream.readLine()) != null) {
            System.out.println(line);
        }
    }

}
