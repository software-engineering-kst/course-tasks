package kz.lakida.javacourse.tishuk.genericDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MyStackArr<E>  {
    private Object[] elementData;
    private int top;

    public MyStackArr(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        elementData = new Object[initialCapacity];
        top = 0;
    }

    public MyStackArr(Collection<? extends E> c) {
        Object[] a = c.toArray();
        top = a.length;
        if (c.getClass() == ArrayList.class) {
            elementData = a;
        } else {
            elementData = Arrays.copyOf(a, top, Object[].class);
        }
    }

    public synchronized void push (E item) throws Exception {
        if (isFull()) {
            elementData = grow();
            addItem(item);
        } else {
            addItem(item);
        }
    }

    private void addItem(E item) {
        System.out.println("Inserting " + item);
        elementData[top] = item;
        top++;
    }

    private Object[] grow() {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity * 2;
        return elementData = Arrays.copyOf(elementData, newCapacity);
    }

    public synchronized E pop() throws EmptyStackException {
        E obj;
        int len = size();
        obj = peek();
        removeElementAt(len - 1);
        return obj;
    }

    public synchronized E peek() throws EmptyStackException {
        int len = size();
        if (len == 0)
            throw new EmptyStackException();
        return (E) elementData[len - 1];
    }

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    private boolean isFull() {
        return this.top == elementData.length;     // или return size() == capacity;
    }

    @Override
    public String toString() {
        return "MyStackArr{" +
                "elementData=" + Arrays.toString(elementData) +
                ", top=" + top +
                ", capacity=" + elementData.length +
                '}';
    }

    private void removeElementAt(int index) {
        if (index >= top) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    top);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = top - index - 1;
        // Тоха, оставилкомент, чтоб тебе показать блок кода из библиотеки Vector. Ниже часть безсмысленная. Она ничего не делает.
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        top--;
        elementData[top] = null;
    }

    public static void main (String[] args) throws Exception {
        MyStackArr stack = new MyStackArr(1);

        stack.push("Denis");
        stack.push("Anton");
        stack.push(123);

        System.out.println(stack);

        stack.pop();
        stack.pop();

        System.out.println("The top element is " + stack.peek());
        System.out.println("The stack size is " + stack.size());

        if (stack.isEmpty()) {
            System.out.println("The stack is empty");
        }
        else {
            System.out.println("The stack is not empty");
        }
             System.out.println(stack);
        }
}
