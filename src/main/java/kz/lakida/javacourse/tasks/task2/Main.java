package kz.lakida.javacourse.tasks.task2;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main<T extends Car> {
    public static void main(String[] args) {
        Car car = new Car();
        Car car1 = new Car(1);
        Car car2 = new Car(2);
        Car car3 = new Car(3);
        List list = new ArrayList();
        list.add(car);
        list.add(car1);
        list.add(car2);
        list.add(car3);
        System.out.println(findMaximum(list));
    }

    public static Car findMaximum(List<Car> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return Collections.max(list);
    }

}

class Car implements Comparable<Car> {
    private final Integer id;

    private final String name = "car: ";

    Car() {
        this.id = 0;
    }

    Car(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + id;
    }

    @Override
    public int compareTo(Car anotherObject) {
        return this.id.compareTo(anotherObject.id);
    }
}
