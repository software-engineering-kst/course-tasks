package kz.lakida.javacourse.tasks.task2.car;


public class SportCar extends Car implements Comparable<Car> {
    private Integer id;
    private String name = "sport car: ";

    public SportCar() {
        this.id = 0;
    }

    public SportCar(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }



}