package kz.lakida.javacourse.tasks.task1;

import kz.lakida.javacourse.tasks.task1.car.Car;

import java.util.ArrayList;
import java.util.List;

public class Stack<T extends Car> {

    private final List<T> arrayList;

    public Stack(){
        arrayList = new ArrayList<>();
    }
    public Stack(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    public void push(T element){
        System.out.println("it's push");
        arrayList.add(element);
    }
    public T pop(){
        System.out.println("it's pop");
        if(arrayList.isEmpty()){
            System.out.println("it's null");
            return null;
        }
        return arrayList.remove(arrayList.size()-1);
    }
    public T peek(){
        System.out.println("it's peek method");
        return arrayList.get(arrayList.size());
    }
    public boolean isEmpty(){
        System.out.println("it's empty: "+ arrayList.isEmpty());

        return arrayList.isEmpty();
    }


}
