package kz.lakida.javacourse.generics.i2;

import java.util.ArrayList;
import java.util.List;

public class Covariance { // Ковариантность
    public static void main(String[] args) {
        var cars = new ArrayList<Car>();
        var trucks = new ArrayList<Truck>();
        var carSum = calculateRemainingFuel(cars);
        var truckSum = calculateRemainingFuel(trucks);
    }

    public static float calculateRemainingFuel(List<? extends Vehicle> vehicles) {
        float sum = 0;
        for (var vehicle : vehicles) {
            sum += vehicle.getRemainingFuel();
        }
        return sum;
    }
}
