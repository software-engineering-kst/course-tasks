package kz.lakida.javacourse.correctoin;

public class Main {
    public static void main(String[] args) {
        PropertyLoader propertyLoader = new PropertyLoader("/home/kuanysh/projects/ExampleCorrection/tasks/taskone/text.txt");
        propertyLoader.loadProperties();
        System.out.println(propertyLoader.getValue("2"));
    }
}
