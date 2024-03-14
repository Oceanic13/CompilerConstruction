package parser;

import java.util.ArrayList;
import java.util.Arrays;

import main.Program;
import scanner.NullToken;
import scanner.Token;
import scanner.Token.Type;
import tree.AssignExpr;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.Expr;
import tree.ForStatement;
import tree.Statement;
import tree.UnaryExpr;
import tree.IfStatement;
import tree.MultiStatement;
import tree.NullExpr;
import tree.PrintStatement;
import tree.VarExpr;
import tree.WhileStatement;
import utils.Stack;

public class Parser {

    private static final Token.Type[] BRACKETS = {Type.LPAREN, Type.RPAREN, Type.LBRACE, Type.RBRACE, Type.LBOXBRACKET, Type.RBOXBRACKET};

    private ArrayList<Token> tokens;
    private int index;
    private Stack<Token.Type> bracketsStack;
    private Program context;

    public Parser() {
        this(new ArrayList<>(Arrays.asList(new Token(Token.Type.EOF, "", null, 0))));
    }

    public Parser(ArrayList<Token> tokens) {
        reset(tokens);
    }

    public Parser(Token...tokens) {
        reset(tokens);
    }

    public Parser reset(Token...tokens) {
        return reset(new ArrayList<>(Arrays.asList(tokens)));
    }

    public Parser reset(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.bracketsStack = new Stack<>();
        return this;
    }

    public Program parse() {
        var context = parseProgram();

        if (!bracketsStack.isEmpty()) {
            System.err.println("Brackets mismatch!");
            System.exit(1);
        }

        return context;
    }

    public Program parseProgram() {
        this.context = new Program(parseMultiStatement());
        return context;
    }

    public Statement parseStatement() {
        switch (peek()) {
            case Token.Type.VAR: return parseVarDeclaration();
            //case Token.Type.IF: return parseIfStatement();
            //case Token.Type.WHILE: return parseWhileStatement();
            //case Token.Type.FOR: return parseForStatement();
            //case Token.Type.PRINT: return parsePrintStatement();
            default: return parseExpression();
        }
    }

    public AssignExpr parseVarDeclaration() {
        eat(Token.Type.VAR);
        Token left = eat(Token.Type.ID);

        Expr right = NullExpr.get();
        if (match(Token.Type.ASSIGN)) {
            right = parseExpression();
        }
        eat(Token.Type.SEMICOLON);

        return new AssignExpr(new VarExpr((int)left.LITERAL), right);
    }

    public MultiStatement parseMultiStatement() {
        var sequence = new MultiStatement();
        while (!isAtEnd() && peek() != Token.Type.RBRACE) {
            sequence.add(parseStatement());
            eat(Token.Type.SEMICOLON);
        }
        return sequence;
    }

    public Expr parseExpression() {
        //TODO
        return null;
    }

    public Expr parseFactor() {
        Expr factor = parseUnary();
        while (peek() == Token.Type.TIMES || peek() == Token.Type.DIV) {
            var token = advance();
            var unary = parseUnary();
            factor = new BinaryExpr(token.TYPE, factor, unary);
        }
        return factor;
    }

    public Expr parseUnary() {
        Token token;
        switch (peek()) {
            case Token.Type.NOT:
            case Token.Type.MINUS:
                token = advance();
                return new UnaryExpr(token.TYPE, parseUnary());
            default:
                return parsePrimary();
        }
    }

    public Expr parsePrimary() {
        var token = advance();
        var type = token.TYPE;
        switch (type) {
            case Token.Type.TRUE: return new ConstExpr(true);
            case Token.Type.FALSE: return new ConstExpr(false);
            case Token.Type.INT:
            case Token.Type.DEC:
            case Token.Type.CHAR:
            case Token.Type.STR: return new ConstExpr(token.LITERAL);
            case Token.Type.ID: return new VarExpr((int)token.LITERAL);
            case Token.Type.LPAREN:
                var expr = parseExpression();
                eat(Token.Type.RPAREN);
                return expr;
            default:
                System.err.printf("Unexpected Token in Primary: %s\n", type);
                System.exit(1);
        }
        return NullExpr.get();
    }
    
    private Token advance() {
        if (isAtEnd()) return NullToken.get();
        var t = tokens.get(index++);

        // Check for brackets mismatch
        for (int i = 0; i < BRACKETS.length/2; ++i) {
            if (t.TYPE == BRACKETS[2*i]) {bracketsStack.push(t.TYPE); break;} //left bracket
            if (t.TYPE == BRACKETS[2*i+1]) { // right bracket
                if (bracketsStack.isEmpty() || bracketsStack.pop() != BRACKETS[2*i]) {
                    System.err.println("Brackets mismatch!");
                    System.exit(1);
                }
            }
        }

        return t;
    }

    private Token eat(Token.Type expected) {
        if (peek() != expected) {
            System.err.printf("Unexpected Token. Expected %s but got %s\n", expected, peek());
            return NullToken.get();
        } else {
            return advance();
        }
    }

    /**
     * If the next token matches the expected type, it is consumed and true is returned.
     * Otherwise, the token is not cosumed and false is returned.
     * @param expected expected token type
     **/
    private boolean match(Token.Type...expected) {
        if (isAtEnd()) return false;
        var peek = peek();
        for (var exp : expected) {
            if (peek == exp) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token.Type peek() {
        if (isAtEnd()) return Token.Type.NULL;
        return tokens.get(index).TYPE;
    }

    public boolean isAtEnd() {
        return index >= tokens.size() || tokens.get(index).TYPE == Token.Type.EOF;
    }
}
