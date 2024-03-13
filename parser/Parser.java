package parser;

import java.util.ArrayList;
import java.util.Arrays;

import main.Context;
import scanner.NullToken;
import scanner.Token;
import scanner.Token.Type;
import tree.AssignExpr;
import tree.Expr;
import tree.ForStatement;
import tree.Statement;
import tree.IfStatement;
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
    private Context context;

    public Parser() {
        this(new ArrayList<>(Arrays. asList(new Token(Token.Type.EOF, "", null, 0))));
    }

    public Parser(ArrayList<Token> tokens) {
        reset(tokens);
    }

    public Parser reset(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.bracketsStack = new Stack<>();
        return this;
    }

    public Context parse() {
        var context = parseProgram();

        if (!bracketsStack.isEmpty()) {
            System.err.println("Brackets mismatch!");
            System.exit(1);
        }

        return context;
    }

    public Context parseProgram() {
        this.context = new Context();
        var sequence = parseBlock();
        for (var s : sequence) context.addStatement(s);
        return new Context(parseBlock());
    }

    public Statement parseStatement() {
        switch (peek()) {
            case Token.Type.VAR: return parseVarDeclaration();
            case Token.Type.IF: return parseIfStatement();
            case Token.Type.WHILE: return parseWhileStatement();
            case Token.Type.FOR: return parseForStatement();
            case Token.Type.PRINT: return parsePrintStatement();
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

        return new AssignExpr(new VarExpr(context.varIndex(left.LEXEME)), right);
    }

    public IfStatement parseIfStatement() {
        eat(Token.Type.IF);
        eat(Token.Type.LPAREN);
        var condition = parseExpression();
        eat(Token.Type.RPAREN);
        var s = new Statement[] {parseStatement()};

        return new IfStatement(condition, s, null);
    }

    public WhileStatement parseWhileStatement() {
        eat(Token.Type.WHILE);
        eat(Token.Type.LPAREN);
        var condition = parseExpression();
        eat(Token.Type.RPAREN);
        var s = new Statement[] {parseStatement()};

        return new WhileStatement(condition, s);
    }

    public ForStatement parseForStatement() {
        eat(Token.Type.FOR);
        eat(Token.Type.LPAREN);
        var initialization = parseStatement();
        eat(Token.Type.SEMICOLON);
        var termination = parseExpression();
        eat(Token.Type.SEMICOLON);
        var increment = parseStatement();
        eat(Token.Type.RPAREN);
        var sequence = new Statement[] {parseStatement()};

        return new ForStatement(initialization, termination, increment, sequence);
    }

    public PrintStatement parsePrintStatement() {
        eat(Token.Type.PRINT);
        return new PrintStatement(parseExpression());
    }

    public Statement[] parseBlock() {
        eat(Token.Type.LBRACE);
        var sequence = new ArrayList<Statement>();
        while (peek() != Token.Type.RBRACE) {
            sequence.add(parseStatement());
        }
        eat(Token.Type.RBRACE);
        return sequence.toArray(new Statement[sequence.size()]);
    }

    public Expr parseExpression() {
        //TODO
        return null;
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
    private boolean match(Token.Type expected) {
        if (isAtEnd() || peek() != expected) return false;
        advance();
        return true;
    }

    private Token.Type peek() {
        if (isAtEnd()) return Token.Type.NULL;
        return tokens.get(index).TYPE;
    }

    private boolean isAtEnd() {
        return index >= tokens.size();
    }
}
