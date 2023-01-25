package kz.lakida.javacourse.tasks.task2;


import kz.lakida.javacourse.tasks.task2.car.Car;
import kz.lakida.javacourse.tasks.task2.car.SportCar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Car car = new Car();
//        Car car1 = new Car(1);
//        Car car2 = new Car(2);
//        Car car3 = new Car(3);
//        List list = new ArrayList();
//        list.add(car);
//        list.add(car1);
//        list.add(car2);
//        list.add(car3);
//        System.out.println(findMaximum(list));

        SportCar sportCar = new SportCar();
        SportCar sportCar1 = new SportCar(1);
        SportCar sportCar2 = new SportCar(2);
        SportCar sportCar3 = new SportCar(3);
        List testList = new ArrayList();
        testList.add(sportCar);
        testList.add(sportCar1);
        testList.add(sportCar2);
        testList.add(sportCar3);
        System.out.println(findMaximum(testList));
    }

    public static Car findMaximum(List<Car> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return Collections.max(list);
    }

}




