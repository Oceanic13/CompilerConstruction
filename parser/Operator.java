package parser;

public class Operator {
    public static final int ARITHMETIC = 0, LOGICAL = 1, ASSIGN = 2;
    public static final int BINARY = 0, UNARY_PREFIX = 1, UNARY_POSTFIX = 2;
    public static final int LEFT_ASSOCIATIVE = 0, RIGHT_ASSOCIATIVE = 1;
    public final int TYPE;
    public final int ARITY;
    public final int ASSOCIATIVITY;
    public final int PRECEDENCE;

    public Operator(int type, int arity, int associativity, int precedence) {
        this.TYPE = type;
        this.ARITY = arity;
        this.ASSOCIATIVITY = associativity;
        this.PRECEDENCE = precedence;
    }
}
