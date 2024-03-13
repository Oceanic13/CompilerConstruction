package utils;

import java.util.Objects;
import java.util.function.Function;

public class TypeCast<A,B> {
    public final Class<A> T1;
    public final Class<B> T2;
    public final Function<A,B> CAST;

    public TypeCast(Class<A> T1, Class<B> T2, Function<A,B> cast) {
        this.T1=T1;
        this.T2=T2;
        this.CAST=cast;
    }

    public B apply(A x) {
        return CAST.apply(x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(T1,T2);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {return false;}
        final var c = (TypeCast<?,?>)o;
        return T1.equals(c.T1) && T2.equals(c.T2);
    }
}
