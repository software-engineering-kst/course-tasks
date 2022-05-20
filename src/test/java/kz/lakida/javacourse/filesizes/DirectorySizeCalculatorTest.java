package kz.lakida.javacourse.filesizes;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DirectorySizeCalculatorTest {

    public static final byte[] TEMP_DATA = new byte[]{0x00};

    @Test
    void shouldCalculateTotalSizeOfAllFiles() throws IOException, PathIsNotAFileException {
        var path = Files.createTempDirectory("directory_size_calculator_test");
        createTestFile(path, "testfile1");
        createTestFile(path, "testfile2");
        createTestFile(path, "testfile3");
        var innerPath = Files.createTempDirectory(path, "inner_directory");
        createTestFile(innerPath, "testfile4");
        createTestFile(innerPath, "testfile5");
        Files.createTempDirectory(path, "empty_directory");

        int result = DirectorySizeCalculator.calculateDirectorySize(path);

        assertThat(result).isEqualTo(5);
    }

    @Test
    void shouldThrowExceptionIfPathIsNotAFolder() throws IOException {
        var file = Files.createTempFile("not_a_directory", "txt");

        assertThatThrownBy(() -> DirectorySizeCalculator.calculateDirectorySize(file))
                .isExactlyInstanceOf(PathIsNotAFileException.class)
                .hasMessage("Path " + file.toAbsolutePath() + " is not a directory");
    }

    private void createTestFile(Path path, String fileName) throws IOException {
        Files.write(Files.createTempFile(path, fileName, "bin"), DirectorySizeCalculatorTest.TEMP_DATA,
                StandardOpenOption.WRITE);
    }

}
