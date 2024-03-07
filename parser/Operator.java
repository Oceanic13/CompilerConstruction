package parser;

import java.util.Map;

import scanner.Token;

import static java.util.Map.entry;  

public class Operator {
    public static enum Type {ARITHMETIC, COMPARISON, LOGICAL, ASSIGN}
    public static enum Arity {BINARY, UNARY_PREFIX, UNARY_POSTFIX}
    public static enum Associativity {LEFT, RIGHT}

    public static final Map<Token.Type, Operator> OPS = Map.ofEntries(
        entry(Token.Type.ADD, new Operator(Type.ARITHMETIC, Arity.BINARY, Associativity.LEFT, 0)),
        entry(Token.Type.SUB, new Operator(Type.ARITHMETIC, Arity.BINARY, Associativity.LEFT, 0)),
        entry(Token.Type.MULT, new Operator(Type.ARITHMETIC, Arity.BINARY, Associativity.LEFT, 10)),
        entry(Token.Type.DIV, new Operator(Type.ARITHMETIC, Arity.BINARY, Associativity.LEFT, 10)),
        entry(Token.Type.POW, new Operator(Type.ARITHMETIC, Arity.BINARY, Associativity.RIGHT, 20)),

        entry(Token.Type.OR, new Operator(Type.LOGICAL, Arity.BINARY, Associativity.LEFT, 30)),
        entry(Token.Type.AND, new Operator(Type.LOGICAL, Arity.BINARY, Associativity.LEFT, 40)),
        entry(Token.Type.NOT, new Operator(Type.LOGICAL, Arity.UNARY_PREFIX, Associativity.RIGHT, 50)),

        entry(Token.Type.EQ, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),
        entry(Token.Type.NEQ, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),
        entry(Token.Type.GEQ, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),
        entry(Token.Type.LEQ, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),
        entry(Token.Type.GREATER, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),
        entry(Token.Type.LESS, new Operator(Type.COMPARISON, Arity.BINARY, Associativity.LEFT, 60)),

        entry(Token.Type.ASSIGN, new Operator(Type.ASSIGN, Arity.BINARY, Associativity.LEFT, 100))
    );

    public final Operator.Type TYPE;
    public final Operator.Arity ARITY;
    public final Operator.Associativity ASSOCIATIVITY;
    public final int PRECEDENCE;

    public Operator(Operator.Type type, Operator.Arity arity, Operator.Associativity associativity, int precedence) {
        this.TYPE = type;
        this.ARITY = arity;
        this.ASSOCIATIVITY = associativity;
        this.PRECEDENCE = precedence;
    }

    public static Operator op(Token.Type t) {return OPS.getOrDefault(t, null);}

    public static Operator op(Token t) {return op(t.TYPE);}
}
