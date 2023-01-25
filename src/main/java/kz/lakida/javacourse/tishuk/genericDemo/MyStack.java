package kz.lakida.javacourse.tishuk.genericDemo;


import java.util.*;

public class MyStack<E> /*extends AbstractList<E> */{
      Object[] elementData;
    protected transient int modCount = 0;
    protected int elementCount;
    protected int capacityIncrement;

//    public MyStack() {
//    }

    public MyStack(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    public MyStack(int initialCapacity) {
        this(initialCapacity, 0);
    }
    public MyStack() {
        this(10);
    }

    public MyStack(Collection<? extends E> c) {
        Object[] a = c.toArray();
        elementCount = a.length;
        if (c.getClass() == ArrayList.class) {
            elementData = a;
        } else {
            elementData = Arrays.copyOf(a, elementCount, Object[].class);
        }
    }
//================================================================
    public E push(E item) {
        addElement(item);
        return item;
    }
    public synchronized void addElement(E obj) {
        modCount++;
        add(obj, elementData, elementCount);
    }

    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
//            elementData = grow();
        elementData[s] = e;
        elementCount = s + 1;
    }

/*    private Object[] grow() {
        return grow(elementCount + 1);
    }*/

/*    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity, *//* minimum growth *//*
                capacityIncrement > 0 ? capacityIncrement : oldCapacity
                *//* preferred growth *//*);
        return elementData = Arrays.copyOf(elementData, newCapacity);
    }*/
    //================================================


    public synchronized E pop() {
        E       obj;
        int     len = size();
        obj = peek();
        removeElementAt(len - 1);
        return obj;
    }

    public synchronized int size() {
        return elementCount;
    }
    //=============================================
    public synchronized E peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    public synchronized void removeElementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = elementCount - index - 1;
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        modCount++;
        elementCount--;
        elementData[elementCount] = null; /* to let gc do its work */
    }

    public synchronized E elementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }
        return elementData(index);
    }
    E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public String toString() {
        return "MyStack{" +
                "elementData=" + Arrays.toString(elementData) +
                ", modCount=" + modCount +
                ", elementCount=" + elementCount +
                ", capacityIncrement=" + capacityIncrement +
                '}';
    }

    //==========================================================================================
public boolean empty() {
    return size() == 0;
}

    public static void main(String[] args) {
        Stack stack = new Stack();

        MyStack<Object> stack1 = new MyStack<>(10);
        stack1.push("Denis");
        stack1.push(1);
        stack1.push("Kuanish");

        System.out.println(stack1);
        System.out.println("++++++++++++++++++++");
    }
}
