package main;
import java.util.ArrayList;

public class Stack<E> implements IStack<E> {

    private ArrayList<E> stack;

    public Stack() {
        this.stack = new ArrayList<E>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void push(E item) {
        stack.add(item);
    }

    @Override
    public E top() {
        assert(!isEmpty());
        return stack.get(size()-1);
    }

    @Override
    public E pop() {
        assert(!isEmpty());
        return stack.remove(size()-1);
    }
}
