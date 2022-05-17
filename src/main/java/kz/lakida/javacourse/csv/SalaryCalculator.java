package kz.lakida.javacourse.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        if (!args[0].isEmpty()) {
            fileName = args[0];
            List<String[]> listLine = new ArrayList<>();
            List<String> listResult = new ArrayList<>();

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                if (args.length == 1) { //&& args[0].endsWith("txt")
                    if (bufferedReader.ready()) {
                        while (bufferedReader.ready()) {
                            listLine.add(bufferedReader.readLine().split(",", 3));
                        }
                    } else {
                        System.out.print("Файл пустой");
                    }
                } else {
                    System.out.print("Нужно указать имя файла");
                }
            } catch (IOException e) {
                System.out.print("Файл не найден");
            }

            for (String[] a : listLine){
                if(a.length != 3){
                    System.out.print("Неправильный формат файла");
                }
            }

            listLine.sort(new ListComparator());

            int sum = 0;
            for (int i = 0; i < listLine.size() - 1; i++) {
                try {
                    if (!listLine.get(i)[2].equals(listLine.get(i + 1)[2])) {
                        sum = sum + Integer.parseInt(listLine.get(i)[1]);
                        listResult.add(listLine.get(i)[2] + ":" + sum);
                        sum = 0;
                    } else {
                        sum = sum + Integer.parseInt(listLine.get(i)[1]);
                    }
                    if (i == listLine.size() - 2) {
                        sum = sum + Integer.parseInt(listLine.get(listLine.size() - 1)[1]);
                        listResult.add(listLine.get(listLine.size() - 1)[2] + ":" + sum);
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

    private static class ListComparator implements Comparator<String[]> {

        @Override
        public int compare(String[] o1, String[] o2) {
            return o1[2].compareTo(o2[2]);
        }
    }

}
