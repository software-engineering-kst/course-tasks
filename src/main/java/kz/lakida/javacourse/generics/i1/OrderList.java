package kz.lakida.javacourse.generics.i1;

import java.util.ArrayList;

public class OrderList {
    private final ArrayList list = new ArrayList();

    public void add(Order order) {
        list.add(order);
    }

    public Order get(int index) {
        return (Order) list.get(index);
    }

    public void remove(int index) {
        list.remove(index);
    }
}
