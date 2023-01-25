package kz.lakida.javacourse.tishuk.genericDemo;
import java.util.*;
import java.util.stream.Collectors;

public class FindMax {
/*    class CompareObject<T> implements Comparator<T>{

        @Override
        public int compare(T o1, T o2) {
            if (o1 == o2)
                return 0;
            else if (o1 > o2)
                return 1;
            else
                return -1;
        }
    }*/

    public static <T> T getMax(Iterable<? extends T>  list) {
        List<Object> newList = new ArrayList<>();
        for(Object l : list) {
            newList.add(l);
        }
        Collections.sort(newList); 
        Collections.reverse(newList);
        return (T) newList.get(0);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(6);
        list.add(2);

        List<String> listSt = new ArrayList<>();
        listSt.add("Яблоко");
        listSt.add("Груша");
        listSt.add("Слива");

        System.out.println("Максимальный элемент числовой = " + getMax(list));

        System.out.println("Максимальный элемент строковый = " + getMax(listSt));

    }


}
