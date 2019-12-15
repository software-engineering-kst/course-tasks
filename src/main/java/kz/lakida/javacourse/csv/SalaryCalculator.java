package kz.lakida.javacourse.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SalaryCalculator {

    /**
     * Вам нужно посчитать общую сумму зарплаты в файле CSV. 
     * Формат файла: <Имя сотрудника>,<Сумма зарплаты, целое число> 
     * Например: Ivan Ivanov,5050
     * 
     * @param args программа должна принимать имя файла как параметр командной
     *             строки
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("Needs to be implemented");
    }

    /**
     * Прочитать файл в список строк.
     * 
     * @param fileName имя файла
     * @return содержимое файла в виде списка строк
     * @throws IOException в случае, если файл не найден либо не удалось прочитать
     *                     файл
     */
    private static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
}
