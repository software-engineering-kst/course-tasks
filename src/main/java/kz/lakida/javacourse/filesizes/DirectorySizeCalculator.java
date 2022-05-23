package kz.lakida.javacourse.filesizes;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

import static java.nio.file.Files.*;
import static java.nio.file.Files.isRegularFile;

public class DirectorySizeCalculator {

    public static void main(String[] args) throws IOException, PathIsNotAFileException {

        int directoryCount = calculateDirectorySize(Path.of(args[0]));
        System.out.println(directoryCount);
    }

    //directory это путь директорий
    public static int calculateDirectorySize(Path directory) throws IOException, PathIsNotAFileException {
        //Проверка на директорию
        if (Files.isDirectory(directory)) {
            //Использую лямбду
            return Files.walk(directory).
                    filter(p -> p.toFile().isFile()).
                    mapToInt(p -> (int) p.toFile().length()).
                    sum();
        } else {
            throw new PathIsNotAFileException("Path " + directory.toAbsolutePath() + " is not a directory");
        }
    }
}
