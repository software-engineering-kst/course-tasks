package kz.lakida.javacourse.generics.i1;

import java.util.ArrayList;
import java.util.List;

public class SumNumbersWithoutType {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println(sumNumbers(list));
    }

    public static int sumNumbers(List<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);

        }
        return sum;
    }
}
