package kz.lakida.javacourse.filesizes;

import java.nio.file.Path;

public class PathIsNotAFileException extends Exception {

    private Path path;

    public PathIsNotAFileException(String message) {
        super(message);
    }
}
