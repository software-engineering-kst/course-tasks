package kz.lakida.javacourse.csv;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SalaryCalculatorTest {
    private PrintStream systemOut;

    private ByteArrayOutputStream newOut;

    @BeforeEach
    void setOutStream() {
        systemOut = System.out;
        newOut = new ByteArrayOutputStream();
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

        assertThat(newOut.toString())
                .as("Неправильный результат")
                .isEqualTo(
                        """
                                IT:400000\r
                                Marketing:100000\r
                                Sales:250000\r
                                """
                );
    }

    @Test
    void calculateSalaryInEmptyFile() throws IOException {
        Path tempFilePath = Files.createTempFile("tempcsv", UUID.randomUUID().toString());

        SalaryCalculator.main(Arrays.array(tempFilePath.toAbsolutePath().toString()));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("Файл пустой");
    }

    @Test
    void calculateSalaryInNonExistingFile() {
        String nonExistingFile = "/tmp/" + UUID.randomUUID();

        SalaryCalculator.main(Arrays.array(nonExistingFile));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("Файл не найден");
    }

    @Test
    void calculateSalaryForFileWithIncorrectFormat() throws IOException {
        String filePath = saveResourceToTempFile("/file_without_separator.csv");

        SalaryCalculator.main(Arrays.array(filePath));

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("Неправильный формат файла");
    }

    @Test
    void calculateSalaryForFileWithIncorrectSalaryFormat() throws IOException {
        String filePath = saveResourceToTempFile("/incorrect_salary_format.csv");

        SalaryCalculator.main(Arrays.array(filePath));

        assertThat(newOut.toString())
                .as("Неправильный результат")
                .isEqualTo("""
                        Marketing:100000\r
                        Sales:150000\r
                        """);
    }

    @Test
    void salaryFileNameIsNotProvided() {
        SalaryCalculator.main(new String[0]);

        assertThat(newOut.toString()).as("Неправильный результат").isEqualTo("Нужно указать имя файла");
    }

    private String saveResourceToTempFile(String resourceName) throws IOException {
        Path tempFilePath = Files.createTempFile("tempcsv", "txt");
        Files.copy(SalaryCalculatorTest.class.getResourceAsStream(resourceName), tempFilePath,
                StandardCopyOption.REPLACE_EXISTING);

        return tempFilePath.toString();
    }
}
