package kz.lakida.javacourse.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SalaryCalculator {

    /**
     * Вам нужно посчитать общую сумму зарплаты по отделам из файла CSV.
     * Формат файла: <Имя сотрудника>,<Сумма зарплаты>,<Название отдела>
     * Например: Ivan Ivanov,5050,IT
     * Формат выходных данных:
     * <Отдел>:<Сумма>
     * <Отдел 2>:<Сумма>
     * Отделы должны быть упорядочены по алфавиту.
     *
     * @param args программа должна принимать имя файла как параметр командной
     *             строки
     */
    public static void main(String[] args) {
        try {
            Files.readAllLines(Paths.get(args[0])).stream()
                    .map(SalaryCalculator::parseCsvString)
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(DepartmentSalary::name, TreeMap::new,
                            Collectors.summingInt(DepartmentSalary::salary)))
                    .forEach(SalaryCalculator::printMapEntry);
        } catch (IOException e) {

        }
    }

    private static void printMapEntry(String key, Integer value) {
        System.out.println(key + ":" + value);
    }

    private static DepartmentSalary parseCsvString(String str) {
        var array = str.split(",");
        try {
            if (array.length == 3) {
                return new DepartmentSalary(array[2], Integer.parseInt(array[1]));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private record DepartmentSalary(String name, int salary) {

    }
}
