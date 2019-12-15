package kz.lakida.javacourse.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalaryCalculatorTest {
    private PrintStream systemOut;
    private final ByteArrayOutputStream newOut = new ByteArrayOutputStream();

    @BeforeEach
    void setOutStream() {
        systemOut = System.out;
        System.setOut(new PrintStream(newOut));
    }

    @AfterEach
    void revertOutStream() {
        System.setOut(systemOut);
    }

    @Test
    void calculateSalaryInCorrectFile() throws IOException {
        String filePath = saveResourceToTempFile("/correct.csv");

        SalaryCalculator.main(Arrays.array(filePath));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("72219");
    }

    @Test
    void calculateSalaryInEmptyFile() throws IOException {
        Path tempFilePath = Files.createTempFile("tempcsv", UUID.randomUUID().toString());

        SalaryCalculator.main(Arrays.array(tempFilePath.toAbsolutePath().toString()));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("0");
    }

    @Test
    void calculateSalaryInNonExistingFile() throws IOException {
        String nonExistingFile = "/tmp/" + UUID.randomUUID().toString();

        SalaryCalculator.main(Arrays.array(nonExistingFile));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("0");
    }

    @Test
    void calculateSalaryForFileWithIncorrectFormat() throws IOException {
        String filePath = saveResourceToTempFile("/file_without_separator.csv");

        SalaryCalculator.main(Arrays.array(filePath));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("0");
    }

    @Test
    void calculateSalaryForFileWithIncorrectSalaryFormat() throws IOException {
        String filePath = saveResourceToTempFile("/incorrect_salary_format.csv");

        SalaryCalculator.main(Arrays.array(filePath));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("2023");
    }

    @Test
    void returnZeroIfFileNameIsNotProvided() {
        SalaryCalculator.main(new String[0]);

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("0");
    }

    private String saveResourceToTempFile(String resourceName) throws IOException {
        Path tempFilePath = Files.createTempFile("tempcsv", UUID.randomUUID().toString());
        Files.copy(SalaryCalculatorTest.class.getResourceAsStream(resourceName), tempFilePath,
                StandardCopyOption.REPLACE_EXISTING);

        return tempFilePath.toString();
    }
}
