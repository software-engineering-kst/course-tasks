package kz.lakida.javacourse.filesizes;

import java.io.IOException;
import java.nio.file.Path;

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
        throw new UnsupportedOperationException("Not implemented");
    }
}
