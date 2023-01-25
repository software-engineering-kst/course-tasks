package kz.lakida.javacourse.tishuk.genericDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MyStackArr<E>  {
    private final Object[] elementData;
    private int top;
    private  int capacity;
    protected transient int modCount = 0;

    // Конструктор для инициализации stack
    MyStackArr(int initialCapacity)
    {
        elementData = new Object[initialCapacity];
        capacity = initialCapacity;
        top = -1;
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
    // Вспомогательная функция для добавления элемента `x` в stack
    public E push (E item) // public void E push (E item) - ругается
    {
        if (isFull())
        {
            System.out.println("Overflow\nProgram Terminated\n");
            System.exit(-1);
        }

        System.out.println("Inserting " + item);
        elementData[++top] = item;
        return item;
    }

    // Вспомогательная функция для извлечения верхнего элемента из stack
    public synchronized E pop()
    {
        //E       obj;
        //int     len = size();
        //obj = peek();
        //removeElementAt(len - 1);
        // проверка на опустошение stack
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }
        System.out.println("Removing " + peek());
        // уменьшаем размер stack на 1 и (необязательно) возвращаем извлеченный элемент
        return (E) elementData[top--];
    }

    // Вспомогательная функция для возврата верхнего элемента stack
    public E peek()
    {
        if (!isEmpty()) {
            return (E) elementData[top];
        }
        else {
            return (E) new RuntimeException("Stack is empty");
        }
    }

    // Вспомогательная функция для возврата размера stack
    public int size() {
        return top + 1;
    }

    // Вспомогательная функция для проверки, пуст stack или нет
    public boolean isEmpty() {
        return top == -1;               // или return size() == 0;
    }

    // Вспомогательная функция для проверки, заполнен ли stack или нет
    public boolean isFull() {
        return top == capacity - 1;     // или return size() == capacity;
    }

    public synchronized void removeElementAt(int index) {
        if (index >= top) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    top);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = top - index - 1;
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        modCount++;
        top--;
        elementData[top] = null; /* to let gc do its work */
    }

    public static void main (String[] args)
    {
        MyStackArr stack = new MyStackArr(3);

        stack.push("Denis");      // вставляем 1 в stack
        stack.push("Anton");      // вставляем 2 в stack

        stack.pop();        // удаление верхнего элемента (2)
        stack.pop();        // удаление верхнего элемента (1)

        stack.push(3);      // вставляем 3 в stack

        System.out.println("The top element is " + stack.peek());
        System.out.println("The stack size is " + stack.size());

        stack.pop();        // удаление верхнего элемента (3)

        // проверяем, пуст ли stack
        if (stack.isEmpty()) {
            System.out.println("The stack is empty");
        }
        else {
            System.out.println("The stack is not empty");
        }
    }
}
