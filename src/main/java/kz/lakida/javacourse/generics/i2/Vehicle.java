package kz.lakida.javacourse.generics.i2;

public abstract class Vehicle {
    private float fuelLeft = 0;

    public float getRemainingFuel() {
        return fuelLeft;
    }

    public void useFuel(float amount) {
        this.fuelLeft -= amount;
    }

    public void addFuel(float amount) {
        this.fuelLeft += amount;
    }
}
