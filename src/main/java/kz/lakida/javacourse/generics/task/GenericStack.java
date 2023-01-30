package kz.lakida.javacourse.generics.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class GenericStack<T> {
    private Node head;
    private int size = 0;
    public synchronized T pop() {
        Node current = head;
        if (current != null) {
            head = current.next;
            size--;
            return current.value;
        } else {
            return null;
        }
    }

    public synchronized void push(T element) {
        head = new Node(element, head);
        size++;
    }

    public synchronized void pushAll(Collection<? extends T> elements) {
        elements.forEach(this::push);
    }

    public synchronized T peek() {
        return head != null ? head.value : null;
    }

    public int size() {
        return size;
    }

    private class Node {
        T value;
        Node next;

        public Node(T value, Node next) {
            Objects.requireNonNull(value);
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        var stack = new GenericStack<A>();

        System.out.println(stack.peek());

        var listOfBs = new ArrayList<B>();
        listOfBs.add(new B());
        listOfBs.add(new B());

        stack.pushAll(listOfBs);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    static class A {}
    static class B extends A {}
}
