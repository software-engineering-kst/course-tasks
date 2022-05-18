package kz.lakida.javacourse.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        String fileName;
        if (args.length != 0) {
            fileName = args[0];
            List<DepartmentSalary> listLine = Collections.emptyList();
            List<String> listResult = new ArrayList<>();

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                if (args.length == 1) { //&& args[0].endsWith("txt")
                    if (bufferedReader.ready()) {
                        listLine = readBufferToList(bufferedReader);
                    } else {
                        System.out.print("Файл пустой");
                    }
                } else {
                    System.out.print("Нужно указать имя файла");
                }
            } catch (IOException e) {
                System.out.print("Файл не найден");
            } catch (IllegalArgumentException e) {
                System.out.print("Неправильный формат файла");
            }

            Collections.sort(listLine);

            int sum = 0;
            for (int i = 0; i < listLine.size() - 1; i++) {
                try {
                    DepartmentSalary currentDepartmentSalary = listLine.get(i);
                    DepartmentSalary nextDepartmentSalary = listLine.get(i + 1);

                    if (currentDepartmentSalary.departmentName.equals(nextDepartmentSalary.departmentName)) {
                        sum = sum + currentDepartmentSalary.salary;
                    } else {
                        sum = sum + currentDepartmentSalary.salary;
                        listResult.add(currentDepartmentSalary.departmentName + ":" + sum);
                        sum = 0;
                    }
                    if (i == listLine.size() - 2) {
                        DepartmentSalary lastSalary = listLine.get(listLine.size() - 1);
                        sum = sum + lastSalary.salary;
                        listResult.add(lastSalary.departmentName + ":" + sum);
                    }
                } catch (NumberFormatException ignored) {

                }
            }

            for (String result : listResult) {
                System.out.println(result);
            }
        } else {
            System.out.print("Нужно указать имя файла");
        }
    }

    private static List<DepartmentSalary> readBufferToList(BufferedReader bufferedReader) throws IOException {
        List<DepartmentSalary> listLine = new ArrayList<>();
        while (bufferedReader.ready()) {
            listLine.add(new DepartmentSalary(bufferedReader.readLine().split(",", 3)));
        }
        return listLine;
    }

    private static class ListComparator implements Comparator<String[]> {

        @Override
        public int compare(String[] o1, String[] o2) {
            return o1[2].compareTo(o2[2]);
        }
    }

    private static class DepartmentSalary implements Comparable<DepartmentSalary> {

        private final String departmentName;

        private final int salary;

        private DepartmentSalary(String departmentName, int salary) {
            this.departmentName = departmentName;
            this.salary = salary;
        }

        private DepartmentSalary(String[] salaryString) {
            if (salaryString.length != 3 && isNumeric(salaryString[1])) {
                throw new IllegalArgumentException("The array must contain 3 elements and second element must be a " +
                        "number");
            }

            this.departmentName = salaryString[2];
            this.salary = Integer.parseInt(salaryString[1]);
        }

        private boolean isNumeric(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public String toString() {
            return this.departmentName + ":" + this.salary;
        }

        @Override
        public int compareTo(DepartmentSalary o) {
            return this.departmentName.compareTo(o.departmentName);
        }
    }

}
