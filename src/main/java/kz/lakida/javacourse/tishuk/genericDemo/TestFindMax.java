package kz.lakida.javacourse.tishuk.genericDemo;

import java.util.ArrayList;
import java.util.List;

public class TestFindMax {
    public static void main(String[] args) {
        var country1 = new Country("Africa", 100, 111);
        var country2 = new Country("Belarus", 500, 555);
        var country3 = new Country("USA", 200, 222);

        List<Country> countryList = new ArrayList<>();
        countryList.add(country1);
        countryList.add(country2);
        countryList.add(country3);
        Country.CountryComparator comparator = new Country.CountryComparator();

        Object ob = FindMax.findMax(countryList, comparator);
        System.out.println(ob);
    }
}
