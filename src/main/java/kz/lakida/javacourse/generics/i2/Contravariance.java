package kz.lakida.javacourse.generics.i2;

import java.util.ArrayList;
import java.util.List;

public class Contravariance { // Контравариантность
    public static void main(String[] args) {
        var cars = new ArrayList<Car>();
        addCar(cars);
        var trucks = new ArrayList<Truck>();
        addTruck(trucks);
        var busses = new ArrayList<Bus>();
        addBus(busses);

        var vehicles = new ArrayList<Vehicle>();
        addCar(vehicles);
        addTruck(vehicles);
        addBus(vehicles);

        var objects = new ArrayList<Object>();
        addCar(objects);
        // PECS
        // Producer - Extends, Consumer - Super
    }

    public static void addCar(List<? super Car> cars) {
        cars.add(new Car());
    }

    public static void addTruck(List<? super Truck> trucks) {
        trucks.add(new Truck());
    }

    public static void addBus(List<? super Bus> busses) {
        busses.add(new Bus());
    }
}
