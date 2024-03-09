package utils;

public class Stack<E> {

    private StackElement top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    public void push(E item) {
        StackElement t = top;
        top = new StackElement();
        top.item = item;
        top.prev = t;
        size++;
    }

    public E top() {
        assert(!isEmpty());
        return top.item;
    }

    public E pop() {
        assert(!isEmpty());
        StackElement t = top;
        top = top.prev;
        size--;
        return t.item;
    }

    private class StackElement {
        private E item;
        private StackElement prev;
    }
}
