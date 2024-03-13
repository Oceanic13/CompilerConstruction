package utils;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import scanner.Token;

public class Operation {
    
    public static class Binary<A,B,C> {
    
        public final Token.Type TYPE;
        public final Class<A> I1;
        public final Class<B> I2;
        public final Class<C> O;
        public final BiFunction<A,B,C> F;

        public Binary(Token.Type type, Class<A> I1, Class<B> I2, Class<C> O, BiFunction<A,B,C> F) {
            this.TYPE = type;
            this.I1 = I1;
            this.I2 = I2;
            this.O = O;
            this.F = F;
        }

        public C apply(A x1, B x2) {
            return F.apply(x1, x2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(TYPE, I1, I2);
        }
    
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {return false;}
            final var b = (Binary<?,?,?>)o;
            return TYPE.equals(b.TYPE) && I1.equals(b.I1) && I2.equals(b.I2);
        }
    }

    public static class Unary<A,B> {
    
        public final Token.Type TYPE;
        public final Class<A> I;
        public final Class<B> O;
        public final Function<A,B> F;

        public Unary(Token.Type type, Class<A> I, Class<B> O, Function<A,B> F) {
            this.TYPE = type;
            this.I = I;
            this.O = O;
            this.F = F;
        }

        public B apply(A x) {
            return F.apply(x);
        }

        @Override
        public int hashCode() {
            return Objects.hash(TYPE, I);
        }
    
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {return false;}
            final var b = (Unary<?,?>)o;
            return TYPE.equals(b.TYPE) && I.equals(b.I);
        }
    }
}
