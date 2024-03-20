package parser;

import java.util.ArrayList;
import java.util.Arrays;

import main.Program;
import scanner.NullToken;
import scanner.Token;
import scanner.Token.Type;
import tree.ArrayExpr;
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
import tree.NullStatement;
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

    /**
     * statement := var_declaration | if_statement | while_statement | for_statement | print_statement | expression
     */
    public Statement parseStatement(boolean expectSemicolonAtEnd) {
        Statement s = NullStatement.get();

        switch (peek()) {
            case Token.Type.VAR: s = parseVarDeclaration(); break;
            case Token.Type.IF: s = parseIfStatement(); break;
            case Token.Type.WHILE: s = parseWhileStatement(); break;
            case Token.Type.FOR: s = parseForStatement(); break;
            case Token.Type.PRINT: s = parsePrintStatement(); break;
            default: s = parseExpression(); break;
        }
        if (expectSemicolonAtEnd) consume(Token.Type.SEMICOLON);
        return s;
    }
    public Statement parseStatement() {return parseStatement(true);}

    public PrintStatement parsePrintStatement() {
        consume(Token.Type.PRINT);
        return new PrintStatement(parseExpression());
    }

    /**
     * if_statement := if "("expression")"" (statement | "{"multi_statement"}") (else statement)?
     */
    public IfStatement parseIfStatement() {
        consume(Token.Type.IF);
        consume(Token.Type.LPAREN);
        var cond = parseExpression();
        consume(Token.Type.RPAREN);

        Statement ifSeq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            ifSeq = parseMultiStatement();
            consume(Token.Type.RBRACE);
        } else {
            ifSeq = parseStatement();
        }
        
        Statement elseSeq = NullStatement.get();
        if (match(Token.Type.ELSE)) {
            if (match(Token.Type.LBRACE)) {
                elseSeq = parseMultiStatement();
                consume(Token.Type.RBRACE);
            } else {
                elseSeq = parseStatement();
            }
        }

        return new IfStatement(cond, ifSeq, elseSeq);
    }

    /**
     * while_statement := while "("expression")"" (statement | "{"multi_statement"}")
     */
    public WhileStatement parseWhileStatement() {
        consume(Token.Type.WHILE);
        consume(Token.Type.LPAREN);
        var cond = parseExpression();
        consume(Token.Type.RPAREN);

        Statement seq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            seq = parseMultiStatement();
            consume(Token.Type.RBRACE);
        } else {
            seq = parseStatement();
        }

        return new WhileStatement(cond, seq);
    }

    public ForStatement parseForStatement() {
        consume(Token.Type.FOR);
        consume(Token.Type.LPAREN);
        var init = parseStatement();
        var cond = parseExpression();
        consume(Token.Type.SEMICOLON);
        var incr = parseStatement(false);
        consume(Token.Type.RPAREN);

        Statement seq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            seq = parseMultiStatement();
            consume(Token.Type.RBRACE);
        } else {
            seq = parseStatement();
        }

        return new ForStatement(init, cond, incr, seq);
    }

    /**
     * var_declaration := var IDENTIFIER "=" expression 
     */
    public AssignExpr parseVarDeclaration() {
        consume(Token.Type.VAR);
        Token left = consume(Token.Type.ID);

        Expr right = NullExpr.get();
        if (match(Token.Type.ASSIGN)) {
            right = parseExpression();
        }

        return new AssignExpr(new VarExpr((int)left.LITERAL), right);
    }

    /**
     * multistatement := (statement)*
     */
    public MultiStatement parseMultiStatement() {
        var sequence = new MultiStatement();
        while (!isAtEnd() && peek() != Token.Type.RBRACE) { // return if reached LBRACE (end of code block)
            sequence.add(parseStatement());
        }
        return sequence;
    }

    /**
     * expression := assignment
     */
    public Expr parseExpression() {
        return parseAssignment();
    }

    /**
     * assignment := IDENTIFIER "=" assignment | logic_or
     */
    public Expr parseAssignment() {
        if (peek(0) == Token.Type.ID && peek(1) == Token.Type.ASSIGN) {
            var left = consume(Token.Type.ID);
            consume(Token.Type.ASSIGN);
            var right = parseAssignment();
            return new AssignExpr(new VarExpr((int)left.LITERAL), right);
        } else {
            return parseLogicOr();
        }
    }

    /**
     * logic_or := logic_and ( "or" logic_and )*
     */
    public Expr parseLogicOr() {
        Expr lor = parseLogicAnd();
        while (peek() == Token.Type.OR) {
            var token = advance();
            var land = parseLogicAnd();
            lor = new BinaryExpr(token.TYPE, lor, land);
        }
        return lor;
    }

    /**
     * logic_and := equality ( "and" equality )*
     */
    public Expr parseLogicAnd() {
        Expr land = parseEquality();
        while (peek() == Token.Type.AND) {
            var token = advance();
            var eq = parseEquality();
            land = new BinaryExpr(token.TYPE, land, eq);
        }
        return land;
    }

    /**
    * equality := comparison ( ( "!=" | "==" ) comparison )*
    **/
    public Expr parseEquality() {
        Expr eq = parseComparison();
        while (peek() == Token.Type.NEQ || peek() == Token.Type.EQ) {
            var token = advance();
            var comp = parseComparison();
            eq = new BinaryExpr(token.TYPE, eq, comp);
        }
        return eq;
    }

    /**
    * comparison := term ( ( ">" | ">=" | "<" | "<=" ) term )*
    **/
    public Expr parseComparison() {
        Expr comp = parseTerm();
        while (peek() == Token.Type.GREATER || peek() == Token.Type.GEQ || peek() == Token.Type.LESS || peek() == Token.Type.LEQ) {
            var token = advance();
            var term = parseTerm();
            comp = new BinaryExpr(token.TYPE, comp, term);
        }
        return comp;
    }

    /**
    * term := factor ( ( "-" | "+" ) factor )*
    **/
    public Expr parseTerm() {
        Expr term = parseFactor();
        while (peek() == Token.Type.MINUS || peek() == Token.Type.PLUS) {
            var token = advance();
            var factor = parseFactor();
            term = new BinaryExpr(token.TYPE, term, factor);
        }
        return term;
    }

    /**
    * factor := exponent ( ( "/" | "*" ) exponent )*
    **/
    public Expr parseFactor() {
        Expr factor = parseExponent();
        while (peek() == Token.Type.TIMES || peek() == Token.Type.DIV) {
            var token = advance();
            var exp = parseExponent();
            factor = new BinaryExpr(token.TYPE, factor, exp);
        }
        return factor;
    }

    /**
     * exponent := unary (^ unary )*
     */
    public Expr parseExponent() {
        Expr exp = parseUnary();
        while (peek() == Token.Type.POW) {
            var token = advance();
            var unary = parseUnary();
            exp = new BinaryExpr(token.TYPE, unary, exp);
        }
        return exp;
    }

    /**
     * unary := := ( "!" | "-" ) unary | primary
     **/
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

    /**
     * primary := ("true" | "false" | NUMBER | STRING | "(" expression ")" | IDENTIFIER | array) ([expression])*
     **/
    public Expr parsePrimary() {
        //var token = advance();
        //var type = token.TYPE;
        Expr primary = NullExpr.get();
        var type = peek();
        switch (type) {
            case Token.Type.TRUE: consume(Token.Type.TRUE); primary = new ConstExpr(true); break;
            case Token.Type.FALSE: consume(Token.Type.FALSE); primary = new ConstExpr(false); break;
            case Token.Type.INT:
            case Token.Type.DEC:
            case Token.Type.CHAR:
            case Token.Type.STR: primary = new ConstExpr(advance().LITERAL); break;
            case Token.Type.ID: primary = new VarExpr((int)advance().LITERAL); break;
            case Token.Type.LBOXBRACKET: primary = parseArray(); break;
            case Token.Type.LPAREN:
                consume(Token.Type.LPAREN);
                primary = parseExpression();
                consume(Token.Type.RPAREN);
                break;
            default:
                System.err.printf("Unexpected Token in Primary Expression: %s\n", type);
                System.exit(1);
        }

        // parse index operation on string, array, identifier or expression
        if (type == Token.Type.ID || type == Token.Type.STR || type == Token.Type.LBOXBRACKET || type == Token.Type.LPAREN) {
            while (match(Token.Type.LBOXBRACKET)) {
                primary = new BinaryExpr(Token.Type.IDX, primary, parseExpression());
                consume(Token.Type.RBOXBRACKET);
            }
        }

        return primary;
    }

    /**
     * array := "[" "]" | "[" expression ("," expression)*  "]"
     **/
    public ArrayExpr parseArray() {
        consume(Token.Type.LBOXBRACKET);
        if (match(Token.Type.RBOXBRACKET)) {
            return new ArrayExpr(new ArrayList<>()); // empty array
        }   

        ArrayList<Expr> exprs = new ArrayList<>();
        exprs.add(parseExpression());
        while (match(Token.Type.COMMA)) {
            exprs.add(parseExpression());
        }
        consume(Token.Type.RBOXBRACKET);

        return new ArrayExpr(exprs);
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

    private Token consume(Token.Type expected) {
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
        return peek(0);
    }

    private Token.Type peek(int o) {
        if (index+o >= tokens.size()) return Token.Type.NULL;
        return tokens.get(index+o).TYPE;
    }

    public boolean isAtEnd() {
        return index >= tokens.size() || tokens.get(index).TYPE == Token.Type.EOF;
    }
}
