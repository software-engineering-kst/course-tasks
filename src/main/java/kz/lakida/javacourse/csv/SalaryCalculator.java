package kz.lakida.javacourse.csv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SalaryCalculator {
    static Map<String, Integer> calculateSalaryInCorrectFile(String fileName) throws IOException {

        String line;
        //создаю структуру данных
        Map<String, Integer> treeMap = new TreeMap<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));

        while ((line = csvReader.readLine()) != null) {

            Scanner scanner = new Scanner(line);
            String department;
            int salary;

            scanner.useDelimiter(",");

            scanner.next();

            if (scanner.hasNextInt()) {
                salary = scanner.nextInt();
                department = scanner.next();
                updateValue(treeMap, department, salary);
            } else {
                scanner.next();
                scanner.next();
            }
        }

        return treeMap;
    }

    //Метод который проверяет имя файла
    public static Boolean salaryFileNameIsNotProvided(String fileName) throws IOException {
        File file = new File(fileName);
        boolean existsFile = file.exists() && !file.isDirectory();
        return existsFile;

    }


    //Метод который проверяет файл пустой или нет
    public static Boolean isFileEmpty(String fileName) throws IOException {
        return Files.size(Paths.get(fileName)) == 0;
    }



    //метод, который проверяет значения на повтор
    public static void updateValue(Map<String, Integer> map, String key, Integer value) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
        } else {
            map.put(key, value);
        }
    }

    //есть ли имя файла
    //мя файла не указано
    //Не существующий файл
    //Не правильный формат файла
    //Файл пустой

    public static void main(String[] args) throws IOException {

        boolean emptyFile = isFileEmpty(args[0]);


        if (emptyFile) {
            System.out.println("Файл пустой");
        } else {
            Map<String, Integer> resultMap = calculateSalaryInCorrectFile(args[0]);
            if (resultMap.isEmpty()) {
                System.out.println("Неверный формат файла");
            } else {
                for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
            }
        }


    }
}


