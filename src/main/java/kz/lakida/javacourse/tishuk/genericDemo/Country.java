package kz.lakida.javacourse.tishuk.genericDemo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Country {
    private int area;
    private String name;
    private int crowded;

    public int getArea() {
        return area;
    }

    public String getName() {
        return name;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCrowded(int crowded) {
        this.crowded = crowded;
    }

    public int getCrowded() {
        return crowded;
    }

    @Override
    public String toString() {
        return "Country{" +
                "area=" + area +
                ", name='" + name + '\'' +
                ", crowded=" + crowded +
                '}';
    }

    public Country(String name, int area, int crowded) {
        this.area = area;
        this.name = name;
        this.crowded = crowded;
    }


    public static class CountryComparator implements Comparator<Country>{
        @Override
        public int compare(Country o1, Country o2) {
            return Integer.compare(o1.area, o2.area);
        }
    }
}
