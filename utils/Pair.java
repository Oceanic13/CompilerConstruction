package utils;

import java.util.Objects;

public class Pair<A,B> {

    public A first;
    public B second;
    
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {return false;}
        final var p = (Pair<?,?>)o;
        return first.equals(p.first) && second.equals(p.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return String.format("<%s, %s>", first, second);
    }
}
