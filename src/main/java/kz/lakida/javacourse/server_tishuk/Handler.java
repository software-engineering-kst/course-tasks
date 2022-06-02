package kz.lakida.javacourse.server_tishuk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Handler extends Thread{


    private static final Map<String, String> CONTENT_TYPES = new HashMap<>(){{
        put("jpg", "image/jpeg");
        put("html", "text/html");
        put("json", "application/json");
        put("txt", "text/plain");
        put("", "text/plain");
    }};
    private Socket socket;
    private String directory;

    Handler(Socket socket, String directory){
        this.socket = socket;
        this.directory = directory;
    }

    @Override
    public void run() {
        try (var input = this.socket.getInputStream(); var output = this.socket.getOutputStream()){
            var url = this.getRequestUrl(input);
            var filePath = Path.of(this.directory, url);
            if (Files.exists(filePath) && Files.isDirectory(filePath)){

            }else {
                // NOT FOUND
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String getRequestUrl(InputStream input){
        var reader = new Scanner(input).useDelimiter((System.lineSeparator()));
        var line = reader.next();
        return line.split(" ")[1];
    }
}
