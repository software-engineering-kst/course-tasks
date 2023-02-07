package kz.lakida.javacourse.tishuk.genericDemo;
import java.util.*;

public class FindMax {
    public static <T extends Comparable<T>> T findMax (Collection<T> elements) {
        T max = null;

        for (T element : elements) {
            if (max == null || max.compareTo(element) < 0) {
                max = element;
            }
        }

        return max;
    }
}
