package kz.lakida.javacourse.tasks.task1;

import kz.lakida.javacourse.tasks.task1.car.SportCar;

public class Main {
    public static void main(String[] args) {
        SportCar sportCar = new SportCar();
        Integer num = 2;
        Stack stack = new Stack();
        stack.push(sportCar);
        stack.pop();
        stack.pop();
        stack.isEmpty();
    }
}
