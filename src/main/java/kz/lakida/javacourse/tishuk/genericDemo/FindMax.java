package kz.lakida.javacourse.tishuk.genericDemo;
import java.util.*;

public class FindMax {
    public static <T> Object findMax (Collection<? extends T> element, Comparator<? super T> comp){
        var list = new ArrayList<>();
        list.addAll(element);
        list.sort((Comparator<? super Object>) comp);
        return list.get(list.size()-1);
    }
}
