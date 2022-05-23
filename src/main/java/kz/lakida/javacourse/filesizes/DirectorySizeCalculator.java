package kz.lakida.javacourse.filesizes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class DirectorySizeCalculator {

    /**
     * Посчитать и сложить размер всех файлов в папке.
     * Нужно так же учесть файлы во вложенных папках.
     *
     * @param path путь для подсчета файлов
     * @return размер всех файлов в байтах
     * @throws IOException             при ошибках файловой системы
     * @throws PathIsNotAFileException в случае, если переданный путь - это не папка
     */
    public static int calculateDirectorySize(Path path) throws IOException, PathIsNotAFileException {
        String pathStr = String.valueOf(path);
        if(!pathStr.contains(".")) {
            for (File file : Objects.requireNonNull(new File(pathStr).listFiles())) {
                BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                if (basicFileAttributes.isRegularFile()) {
                    sizes += basicFileAttributes.size();
                } else {
                    calculateDirectorySize(Path.of(file.toString()));
                }
            }
        } else {
            throw new PathIsNotAFileException("Path " + pathStr + " is not a directory");
        }
        return sizes;
    }
    // new UnsupportedOperationException("Not implemented");


    private StringBuffer sb;
    private static int sizes;
    public static void main(String[] args) throws IOException, PathIsNotAFileException {
        Path path = Paths.get(args[0]);
        calculateDirectorySize(path);
        System.out.println(sizes/1024);
    }
}
