package kz.lakida.javacourse.tasks.task2.car;


public class Car implements Comparable<Car> {


    private String name = "car: ";
    private Integer id;

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
    public int compareTo(Car o) {
        return this.id.compareTo(o.id);
    }


//    @Override
//    public int compareTo(T anotherObject) {
//        return this.id.compareTo(anotherObject.id);
//    }



}