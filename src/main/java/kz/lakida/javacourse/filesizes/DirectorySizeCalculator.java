package kz.lakida.javacourse.filesizes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectorySizeCalculator {

    /**
     * Посчитать и сложить размер всех файлов в папке.
     * Нужно так же учесть файлы во вложенных папках.
     *
     * @param  args для подсчета файлов
     * @return размер всех файлов в байтах
     * @throws IOException             при ошибках файловой системы
     * @throws PathIsNotAFileException в случае, если переданный путь - это не папка
     */
    public static void main(String[] args) throws IOException, PathIsNotAFileException {
        Path path = Paths.get(args[0]);
       /* if(path.isAbsolute()){
            throw new PathIsNotAFileException("Path " + args[0] + " is not a directory");
        }*/
        if(args[0].contains(".")){
            throw new PathIsNotAFileException("Path " + args[0] + " is not a directory");
        }
        try {
            Path testFile1 = Files.createFile(Paths.get(args[0]));
            throw new PathIsNotAFileException("Path " + args[0] + " is not a directory");
        } catch (IOException ignored) {
        }

        if(path.endsWith(".txt")){
            throw new PathIsNotAFileException("Path " + args[0] + " is not a directory");
        }

        int directorySize = calculateDirectorySize(path);
        System.out.println(directorySize);
    }
    public static int calculateDirectorySize(Path path) throws IOException, PathIsNotAFileException {
        try {
            Path folder = Paths.get(String.valueOf(path)); // Сначала создадим объект папки с помощью метода
            return (int) Files.walk(folder) // Затем для обхода всей иерархии файлов используем метод walk.
                    // Он возвращает поток (Stream) из элементов типа Path
                    .map(Path::toFile) // Эти элементы мы преобразуем в объекты типа File с помощью метода потока map()
                    .filter(File::isFile) // Затем оставляем только те элементы, которые действительно являются файлами (метод isFile())
                    .mapToLong(File::length) // Потом у каждого файла берём его размер (метод length())
                    .sum(); // и в конце все значения суммируем
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Not implemented");
        }catch (IOException e) {
            throw new IOException("Ошибка файловой системы");
        }

        //throw new UnsupportedOperationException("Not implemented");
    }
}
