package kz.lakida.javacourse.networking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class WebServerTest {

    private static final String WEB_SERVER_ROOT_INDEX = """
            <!doctype html>
            <html>
                <head>
                    <title>Index page</title>
                    <meta name="description" content="Our index page">
                    <meta name="keywords" content="webserver tutorial task">
                </head>
                <body>
                    <h1>Index page</h1>
                </body>
            </html>
            """;

    private static final String ORDERS_INDEX_CONTENTS = """
            <!doctype html>
            <html>
                <head>
                    <title>Orders index</title>
                    <meta name="description" content="Our first page">
                    <meta name="keywords" content="webserver tutorial task">
                </head>
                <body>
                    Orders main page.
                </body>
            </html>
            """;

    private static final String ORDERS_PAGE_CONTENTS = """
            <!doctype html>
            <html>
                <head>
                    <title>Some page</title>
                    <meta name="description" content="Our first page">
                    <meta name="keywords" content="webserver tutorial task">
                </head>
                <body>
                    Orders secondary page.
                </body>
            </html>
            """;

    private static final String TEXTS_DIRECTORY_CONTENTS = """
            <!doctype html>
            <html>
                <head>
                    <title>/texts</title>
                </head>
                <body>
                    <h3>
                        Contents of the folder <i>texts</i>
                    </h3>
                    <ul>
                        <li><a href="/texts/1.txt">1.txt</a></li>
                        <li><a href="/texts/2.txt">2.txt</a></li>
                        <li><a href="/texts/3.txt">3.txt</a></li>
                    </ul>
                </body>
            </html>
            """;

    private static final String NOT_FOUND_CONTENTS = """
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

    private static final int MAXIMUM_ALLOWED_PORT_NUMBER = 20000;

    private static int PORT = 8080;

    private static WebServer WEB_SERVER = null;

    @BeforeAll
    static void initializeWebServer() throws IOException {
        /* Создаем структуру папок для веб сервера
        - index.html
        - /orders
        -     index.html
        -     orders.html
        - /texts
        -     1.txt
        -     2.txt
        -     3.txt
        */
        var webServerRoot = Files.createTempDirectory("webserver");
        var webServerRootIndex = Files.createFile(webServerRoot.resolve("index.html"));
        Files.writeString(webServerRootIndex, WEB_SERVER_ROOT_INDEX);
        var ordersPath = Files.createDirectory(webServerRoot.resolve("orders"));
        var orderIndex = Files.createFile(ordersPath.resolve("index.html"));
        var ordersPage = Files.createFile(ordersPath.resolve("orders.html"));
        Files.writeString(orderIndex, ORDERS_INDEX_CONTENTS);
        Files.writeString(ordersPage, ORDERS_PAGE_CONTENTS);
        var textsPath = Files.createDirectory(webServerRoot.resolve("texts"));
        Files.createFile(textsPath.resolve("1.txt"));
        Files.createFile(textsPath.resolve("2.txt"));
        Files.createFile(textsPath.resolve("3.txt"));

        // Запускаем веб сервер. Ищем свободный порт
        while (WEB_SERVER == null && PORT < MAXIMUM_ALLOWED_PORT_NUMBER) {
            try {
                WEB_SERVER = new WebServer(PORT, webServerRoot);
            } catch (IOException ignored) {
                PORT++;
            }
        }
        if (WEB_SERVER == null) {
            throw new IOException("Port range is exceeded, could not initialize the web server");
        }
    }

    @AfterAll
    static void stopWebServer() {
        if (WEB_SERVER != null) {
            WEB_SERVER.stop();
        }
    }

    @Test
    @DisplayName("Тестируем главную страницу (файл index.html в корневой папке)")
    void shouldReturnRootPage() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT))
                .GET()
                .build();

        HttpResponse<String> result = sendHttpRequest(request);

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo(WEB_SERVER_ROOT_INDEX);
    }

    @Test
    @DisplayName("Тестируем страницу по умолчанию в папке orders (файл index.html)")
    void shouldReturnOrdersIndex() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/orders"))
                .GET()
                .build();

        HttpResponse<String> result = sendHttpRequest(request);

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo(ORDERS_INDEX_CONTENTS);
    }

    @Test
    @DisplayName("Тестируем страницу в папке orders (файл orders.html)")
    void shouldReturnOrdersPage() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/orders/orders.html"))
                .GET()
                .build();

        HttpResponse<String> result = sendHttpRequest(request);

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo(ORDERS_PAGE_CONTENTS);
    }

    @Test
    @DisplayName("Тестируем страницу со списком файлов в директории, если в ней нет файла index.html")
    void shouldListFilesInDirectoryIfIndexDoesNotExist() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/texts"))
                .GET()
                .build();

        HttpResponse<String> result = sendHttpRequest(request);

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo(TEXTS_DIRECTORY_CONTENTS);
    }

    @Test
    @DisplayName("Тестируем ответ когда файл или папка не существует")
    void shouldListFilesInDirectoryIfPageDoesNotExist() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/nonexistent"))
                .GET()
                .build();

        HttpResponse<String> result = sendHttpRequest(request);

        assertThat(result.statusCode()).isEqualTo(404);
        assertThat(result.body()).isEqualTo(NOT_FOUND_CONTENTS);
    }

    private HttpResponse<String> sendHttpRequest(HttpRequest request) {
        try {
            var client = HttpClient.newHttpClient();
            return client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new AssertionError("Could not make an HTTP call", e);
        }
    }

}
