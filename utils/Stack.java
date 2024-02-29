package utils;

public class Stack<E> implements IStack<E> {

    private StackElement top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(E item) {
        StackElement t = top;
        top = new StackElement();
        top.item = item;
        top.prev = t;
        size++;
    }

    @Override
    public E top() {
        assert(!isEmpty());
        return top.item;
    }

    @Override
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
