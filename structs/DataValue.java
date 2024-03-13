package structs;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import scanner.Token;
import utils.TypeCast;
import utils.Operation.Binary;
import utils.Operation.Unary;

public class DataValue<A> {

    private A v;

    private static HashMap<Class<?>, HashMap<Class<?>, TypeCast<?,?>>> TYPECASTS = new HashMap<>();
    private static HashMap<Token.Type, HashMap<Class<?>, HashMap<Class<?>, Binary<?,?,?>>>> BINARY_OPS = new HashMap<>();
    private static HashMap<Token.Type, HashMap<Class<?>, Unary<?,?>>> UNARY_OPS = new HashMap<>();

    public static void init() {
        defTypeCast(Boolean.class, Boolean.class, x -> x);
        defTypeCast(Boolean.class, Integer.class, x -> x?1:0);
        defTypeCast(Boolean.class, Double.class, x -> x?1.:0.);
        defTypeCast(Boolean.class, Character.class, x -> x?'1':'0');
        defTypeCast(Boolean.class, String.class, x -> x?"true":"false");

        defTypeCast(Integer.class, Boolean.class, x -> x!=0);
        defTypeCast(Integer.class, Integer.class, x -> x);
        defTypeCast(Integer.class, Double.class, x -> (double)x);
        defTypeCast(Integer.class, Character.class, x -> (char)x.intValue());
        defTypeCast(Integer.class, String.class, x -> ""+x);

        defTypeCast(Double.class, Boolean.class, x -> x!=0.);
        defTypeCast(Double.class, Integer.class, x -> x.intValue());
        defTypeCast(Double.class, Double.class, x -> x);
        defTypeCast(Double.class, Character.class, x -> (char)x.intValue());
        defTypeCast(Double.class, String.class, x -> ""+x);

        defTypeCast(Character.class, Boolean.class, x -> x!='0');
        defTypeCast(Character.class, Integer.class, x -> (int)x);
        defTypeCast(Character.class, Double.class, x -> (double)x);
        defTypeCast(Character.class, Character.class, x -> x);
        defTypeCast(Character.class, String.class, x -> ""+x);

        defTypeCast(String.class, Boolean.class, x -> x.length()>0);
        defTypeCast(String.class, Integer.class, x -> x.length());
        defTypeCast(String.class, Double.class, x -> (double)x.length());
        //defTypeCast(String.class, Character.class, x -> x.charAt(0));
        defTypeCast(String.class, String.class, x -> x);

        // Logical operations
        defOp(Token.Type.NOT, Boolean.class, Boolean.class, x->!x);
        defSymmetricOp(Token.Type.AND, Boolean.class, Boolean.class, Boolean.class, (x,y) -> x&&y);
        defSymmetricOp(Token.Type.OR, Boolean.class, Boolean.class, Boolean.class, (x,y) -> x||y);

        // Numeric, arithmetic operations
        defSymmetricOp(Token.Type.ADD, Integer.class, Integer.class, Integer.class, (x,y) -> x+y);
        defSymmetricOp(Token.Type.ADD, Double.class, Double.class, Double.class, (x,y) -> x+y);
        defSymmetricOp(Token.Type.ADD, Integer.class, Double.class, Double.class, (x,y) -> x+y);
        defOp(Token.Type.SUB, Integer.class, Integer.class, Integer.class, (x,y) -> x-y);
        defOp(Token.Type.SUB, Integer.class, Double.class, Double.class, (x,y) -> x-y);
        defOp(Token.Type.SUB, Double.class, Integer.class, Double.class, (x,y) -> x-y);
        defOp(Token.Type.SUB, Double.class, Double.class, Double.class, (x,y) -> x-y);
        defSymmetricOp(Token.Type.MULT, Integer.class, Integer.class, Integer.class, (x,y) -> x*y);
        defSymmetricOp(Token.Type.MULT, Double.class, Double.class, Double.class, (x,y) -> x*y);
        defSymmetricOp(Token.Type.MULT, Integer.class, Double.class, Double.class, (x,y) -> x*y);
        defOp(Token.Type.DIV, Integer.class, Integer.class, Integer.class, (x,y) -> x/y);
        defOp(Token.Type.DIV, Integer.class, Double.class, Double.class, (x,y) -> x/y);
        defOp(Token.Type.DIV, Double.class, Integer.class, Double.class, (x,y) -> x/y);
        defOp(Token.Type.DIV, Double.class, Double.class, Double.class, (x,y) -> x/y);

        defSymmetricOp(Token.Type.ADD, Integer.class, String.class, String.class, (x,y) -> x+y);

        defOp(Token.Type.SUB, String.class, Character.class, String.class, (x,y) -> x.replaceAll(""+y, ""));
        defOp(Token.Type.SUB, String.class, String.class, String.class, (x,y) -> x.replaceAll(y, ""));

        defSymmetricOp(Token.Type.MULT, String.class, Integer.class, String.class, (x,y) -> y>=0? x.repeat(y) : new StringBuilder(x.repeat(-y)).reverse().toString());
    }
    
    public static <A,B> void defTypeCast(Class<A> T1, Class<B> T2, Function<A, B> cast) {
        TYPECASTS.putIfAbsent(T1, new HashMap<>());
        TYPECASTS.get(T1).putIfAbsent(T2, new TypeCast<>(T1, T2, cast));
    }

    public static <A,B> void defOp(Token.Type type, Class<A> I, Class<B> O, Function<A, B> f) {
        UNARY_OPS.putIfAbsent(type, new HashMap<>());
        UNARY_OPS.get(type).putIfAbsent(I, new Unary<>(type, I, O, f));
    }

    public static <A,B,C> void defOp(Token.Type type, Class<A> I1, Class<B> I2, Class<C> O, BiFunction<A,B,C> f) {
        BINARY_OPS.putIfAbsent(type, new HashMap<>());
        BINARY_OPS.get(type).putIfAbsent(I1, new HashMap<>());
        BINARY_OPS.get(type).get(I1).putIfAbsent(I2, new Binary<>(type, I1, I2, O, f));
    }

    public static <A,B,C> void defSymmetricOp(Token.Type type, Class<A> I1, Class<B> I2, Class<C> O, BiFunction<A,B,C> f) {
        defOp(type, I1, I2, O, f);
        defOp(type, I2, I1, O, (x,y) -> f.apply(y,x));
    }

    public DataValue(DataValue<A> o) {
        this.v = o.v;
    }

    public  DataValue(A v) {
        this.v = v;
    }

    public A value() {
        return v;
    }

    @SuppressWarnings("unchecked")
    public Class<A> type() {
        return (Class<A>) v.getClass();
    }

    public <B> DataValue<B> cast(Class<B> c2) {

        var tc1 = TYPECASTS.getOrDefault(v.getClass(), null);
        if (tc1 != null) {
            @SuppressWarnings("unchecked")
            var tc = (TypeCast<A,B>)tc1.getOrDefault(c2, null);
            if (tc != null) {
                return new DataValue<B>(tc.apply(this.v));
            }
        }
        throw new ClassCastException(String.format("No Cast defined from %s to %s!", this.v.getClass().getSimpleName(), c2.getSimpleName()));
    }

    public <B> DataValue<B> apply(Token.Type type) {
        var t = UNARY_OPS.getOrDefault(type, null);
        if (t != null) {
            @SuppressWarnings("unchecked")
            var u = (Unary<A,B>)t.getOrDefault(this.v.getClass(), null);
            if (u != null) {
                return new DataValue<B>(u.apply(this.v));
            }
        }
        throw new UnsupportedOperationException(String.format("No Unary Operation %s defined for %s!", type.name(), this.v.getClass().getSimpleName()));
    }

    public <B,C> DataValue<C> apply(Token.Type type, DataValue<B> rhs) {
        var t1 = BINARY_OPS.getOrDefault(type, null);
        if (t1 != null) {
            var t2 = BINARY_OPS.get(type).getOrDefault(this.v.getClass(), null);
            if (t2 != null) {
                @SuppressWarnings("unchecked")
                var b = (Binary<A,B,C>)t2.getOrDefault(rhs.v.getClass(), null);
                if (b != null) {
                    return new DataValue<C>(b.apply(this.v, rhs.v));
                }
            }
        }
        throw new UnsupportedOperationException(String.format("No Binary Operation %s defined for %s!", type.name(), this.v.getClass().getSimpleName()));
    }

    public boolean eq(DataValue<?> o) {return v.equals(o.v);}
    public boolean neq(DataValue<?> o) {return !eq(o);}

    @Override
    public String toString() {
        return String.format("<%s, %s>", v.getClass().getSimpleName(), v.toString());
    }
}
