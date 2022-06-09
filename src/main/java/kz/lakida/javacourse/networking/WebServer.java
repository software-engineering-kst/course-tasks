package kz.lakida.javacourse.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebServer implements AutoCloseable {

    public static void main(String[] args) throws IOException {
        var root = Paths.get("/Users/antonlakida/web_root");
        new WebServer(8081, root);
    }

    private final ServerSocket serverSocket;

    private final ExecutorService serverSocketPool = Executors.newSingleThreadExecutor();

    private final ExecutorService clientSocketPool = Executors.newCachedThreadPool();

    private final Path webServerRoot;

    public WebServer(int port, Path webServerRoot) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.webServerRoot = webServerRoot;
        serverSocketPool.submit(new NewConnectionListener());
    }

    public void stop() {
        serverSocketPool.shutdown();
        try {
            serverSocketPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        this.stop();
    }

    private class NewConnectionListener implements Runnable {

        @Override
        public void run() {
            while (!serverSocket.isClosed()) {
                try {
                    var socket = serverSocket.accept();
                    clientSocketPool.submit(new ConnectionProcessor(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ConnectionProcessor implements Runnable {

        private static final String NOT_FOUND = """
                HTTP/1.1 404 NOT FOUND
                Content-Type: text/html
                Connection: close

                <!doctype html>
                <html>
                    <head>
                        <title>Not found</title>
                    </head>
                    <body>
                        <h1>Page you requested was not found on this server</h1>
                    </body>
                </html>
                """;

        private final Socket socket;

        private final BufferedReader inputStream;

        private final OutputStream outputStream;

        private ConnectionProcessor(Socket socket) throws IOException {
            this.socket = socket;
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = socket.getOutputStream();
        }

        @Override
        public void run() {
            try (socket; inputStream; outputStream) {
                var request = readRequest();
                System.out.println("Request came");
                request.forEach(System.out::println);
                var content = request.stream().findFirst()
                        .map(s -> {
                            var array = s.split(" ");
                            System.out.println(Arrays.toString(array));
                            return array;
                        })
                        .map(array -> array[1])
                        .map(pathString -> {
                            System.out.println(pathString.substring(1));
                            return pathString.substring(1);
                        })
                        .map(webServerRoot::resolve)
                        .flatMap(this::readFileLines)
                        .map(this::mapSuccessfulContent)
                        .orElse(NOT_FOUND);

                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private String mapSuccessfulContent(String content) {
            return "HTTP/1.1 200 OK\n" +
                   "Content-Type: text/html\n" +
                   "Connection: close\n\n" +
                   String.join("\n", content) +
                   "\n";
        }

        private Optional<String> readFileLines(Path path) {
            if (Files.isDirectory(path)) {
                Path indexPath = path.resolve("index.html");
                if (Files.exists(indexPath)) {
                    return readSilently(indexPath);
                } else {
                    return Optional.of(pathToDirectoryListing(path));
                }
            } else {
                return readSilently(path);
            }
        }

        private Optional<String> readSilently(Path path) {
            try {
                return Optional.of(String.join("\n", Files.readAllLines(path)));
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }

        private String pathToDirectoryListing(Path path) {
            try {
                List<String> fileNameHtml = Files.walk(path, 1)
                        .skip(1)
                        .map(file -> file.getFileName().toString())
                        .sorted()
                        .map(fileName -> "            <li><a href=\"/" + path.getFileName() + "/" + fileName + "\">" + fileName + "</a></li>")
                        .collect(Collectors.toList());

                return "<!doctype html>\n" +
                       "<html>\n" +
                       "    <head>\n" +
                       "        <title>/" + path.getFileName() + "</title>\n" +
                       "    </head>\n" +
                       "    <body>\n" +
                       "        <h3>\n" +
                       "            Contents of the folder <i>" + path.getFileName() + "</i>\n" +
                       "        </h3>\n" +
                       "        <ul>\n" +
                       String.join("\n", fileNameHtml) + "\n" +
                       "        </ul>\n" +
                       "    </body>\n" +
                       "</html>";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        private List<String> readRequest() throws IOException {
            var list = new ArrayList<String>();
            String line;
            while ((line = inputStream.readLine()) != null && !line.isEmpty()) {
                list.add(line);
            }
            return list;
        }
    }
}
