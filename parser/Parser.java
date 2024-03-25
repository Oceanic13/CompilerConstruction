package parser;

import java.util.ArrayList;
import java.util.Arrays;

import main.Program;
import main.Scope;
import scanner.NullToken;
import scanner.Token;
import scanner.Token.Type;
import tree.ArrayExpr;
import tree.AssignExpr;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.Expr;
import tree.ForStatement;
import tree.FuncCallExpr;
import tree.Statement;
import tree.UnaryExpr;
import tree.VarDeclExpr;
import tree.IfStatement;
import tree.MultiStatement;
import tree.NullExpr;
import tree.NullStatement;
import tree.PrintStatement;
import tree.ReturnStatement;
import tree.VarExpr;
import tree.WhileStatement;
import utils.Stack;

public class Parser {

    //TODO: Handle Semicolon correctly (smarter)

    private static final Token.Type[] BRACKETS = {Type.LPAREN, Type.RPAREN, Type.LBRACE, Type.RBRACE, Type.LBOXBRACKET, Type.RBOXBRACKET};

    private ArrayList<Token> tokens;
    private int index;
    private Stack<Token.Type> bracketsStack;
    private Program context;
    private Scope globalScope;
    //private Scope globalScope;

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
        parseProgram();

        if (!bracketsStack.isEmpty()) {
            System.err.println("Brackets mismatch!");
            System.exit(1);
        }

        return context;
    }

    public Program parseProgram() {
        this.globalScope = new Scope();
        this.context = new Program(globalScope, parseMultiStatement(globalScope));
        return context;
    }

    /**
     * statement := var_declaration | if_statement | while_statement | for_statement | print_statement | expression
     */
    public Statement parseStatement(Scope scope, boolean expectSemicolonAtEnd) {
        Statement s = NullStatement.get();

        switch (peek()) {
            case Token.Type.VAR: s = parseVarDeclaration(scope); consume(Token.Type.SEMICOLON); break;
            case Token.Type.FUNC: parseFuncDeclaration(); break;
            case Token.Type.IF: s = parseIfStatement(scope); break;
            case Token.Type.WHILE: s = parseWhileStatement(scope); break;
            case Token.Type.FOR: s = parseForStatement(scope); break;
            case Token.Type.PRINT: s = parsePrintStatement(scope); consume(Token.Type.SEMICOLON); break;
            case Token.Type.RETURN: s = parseReturnStatement(scope); consume(Token.Type.SEMICOLON); break;
            default: s = parseExpression(scope); if (expectSemicolonAtEnd) consume(Token.Type.SEMICOLON); break;
        }
        return s;
    }
    public Statement parseStatement(Scope scope) {return parseStatement(scope, true);}

    public PrintStatement parsePrintStatement(Scope scope) {
        consume(Token.Type.PRINT);
        return new PrintStatement(parseExpression(scope));
    }

    public ReturnStatement parseReturnStatement(Scope scope) {
        consume(Token.Type.RETURN);
        return new ReturnStatement(parseExpression(scope));
    } 

    /**
     * if_statement := if "("expression")"" (statement | "{"multi_statement"}") (else statement)?
     */
    public IfStatement parseIfStatement(Scope scope) {
        Scope ifScope = new Scope(scope);

        consume(Token.Type.IF);
        consume(Token.Type.LPAREN);
        var cond = parseExpression(ifScope);
        consume(Token.Type.RPAREN);

        Statement ifSeq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            ifSeq = parseMultiStatement(ifScope);
            consume(Token.Type.RBRACE);
        } else {
            ifSeq = parseStatement(ifScope);
        }
        
        Statement elseSeq = NullStatement.get();
        Scope elseScope = new Scope(scope);
        if (match(Token.Type.ELSE)) {
            if (match(Token.Type.LBRACE)) {
                elseSeq = parseMultiStatement(elseScope);
                consume(Token.Type.RBRACE);
            } else {
                elseSeq = parseStatement(elseScope);
            }
        }

        return new IfStatement(cond, ifSeq, elseSeq);
    }

    /**
     * while_statement := while "("expression")"" (statement | "{"multi_statement"}")
     */
    public WhileStatement parseWhileStatement(Scope scope) {
        var headScope = scope;//new Scope(scope);
        var bodyScope = new Scope(headScope);

        consume(Token.Type.WHILE);
        consume(Token.Type.LPAREN);
        var cond = parseExpression(headScope);
        consume(Token.Type.RPAREN);

        Statement seq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            seq = parseMultiStatement(bodyScope);
            consume(Token.Type.RBRACE);
        } else {
            seq = parseStatement(bodyScope);
        }

        return new WhileStatement(cond, seq);
    }

    public ForStatement parseForStatement(Scope scope) {
        Scope headScope = new Scope(scope);
        Scope bodyScope = new Scope(headScope);
        consume(Token.Type.FOR);
        consume(Token.Type.LPAREN);
        var init = parseStatement(headScope);
        var cond = parseExpression(headScope);
        consume(Token.Type.SEMICOLON);
        var incr = parseStatement(headScope, false);
        consume(Token.Type.RPAREN);

        Statement seq = NullStatement.get();
        if (match(Token.Type.LBRACE)) {
            seq = parseMultiStatement(bodyScope);
            consume(Token.Type.RBRACE);
        } else {
            seq = parseStatement(bodyScope);
        }

        return new ForStatement(init, cond, incr, seq);
    }

    /**
     * var_declaration := var IDENTIFIER "=" expression 
     */
    public VarDeclExpr parseVarDeclaration(Scope scope) {
        consume(Token.Type.VAR);
        Token left = consume(Token.Type.ID);

        Expr right = NullExpr.get();
        if (match(Token.Type.ASSIGN)) {
            right = parseExpression(scope);
        }

        return new VarDeclExpr(new VarExpr(scope, left.LEXEME), right);
    }

    public void parseFuncDeclaration() {
        //TODO: Handle Scope correctly
        var funcScope = new Scope(globalScope);
        consume(Token.Type.FUNC);
        var idToken = consume(Token.Type.ID);
        consume(Token.Type.LPAREN);
        var args = parseIdList();
        consume(Token.Type.RPAREN);
        consume(Token.Type.LBRACE);
        var seq = parseMultiStatement(funcScope);
        consume(Token.Type.RBRACE);

        globalScope.declFunc(idToken.LEXEME, args.toArray(new String[]{}), seq);
    }

    /**
     * multistatement := (statement)*
     */
    public MultiStatement parseMultiStatement(Scope scope) {
        var sequence = new MultiStatement(scope);

        while (!isAtEnd() && peek() != Token.Type.RBRACE) { // return if reached LBRACE (end of code block)
            sequence.add(parseStatement(scope));
        }
        return sequence;
    }

    /**
     * expression := assignment
     */
    public Expr parseExpression(Scope scope) {
        return parseAssignment(scope);
    }

    /**
     * assignment := IDENTIFIER "=" assignment | logic_or
     */
    public Expr parseAssignment(Scope scope) {
        if (peek(0) == Token.Type.ID && peek(1) == Token.Type.ASSIGN) {
            var left = consume(Token.Type.ID);
            consume(Token.Type.ASSIGN);
            var right = parseAssignment(scope);
            return new AssignExpr(new VarExpr(scope, left.LEXEME), right);
        } else {
            return parseLogicOr(scope);
        }
    }

    /**
     * logic_or := logic_and ( "or" logic_and )*
     */
    public Expr parseLogicOr(Scope scope) {
        Expr lor = parseLogicAnd(scope);
        while (peek() == Token.Type.OR) {
            var token = advance();
            var land = parseLogicAnd(scope);
            lor = new BinaryExpr(token.TYPE, lor, land);
        }
        return lor;
    }

    /**
     * logic_and := equality ( "and" equality )*
     */
    public Expr parseLogicAnd(Scope scope) {
        Expr land = parseEquality(scope);
        while (peek() == Token.Type.AND) {
            var token = advance();
            var eq = parseEquality(scope);
            land = new BinaryExpr(token.TYPE, land, eq);
        }
        return land;
    }

    /**
    * equality := comparison ( ( "!=" | "==" ) comparison )*
    **/
    public Expr parseEquality(Scope scope) {
        Expr eq = parseComparison(scope);
        while (peek() == Token.Type.NEQ || peek() == Token.Type.EQ) {
            var token = advance();
            var comp = parseComparison(scope);
            eq = new BinaryExpr(token.TYPE, eq, comp);
        }
        return eq;
    }

    /**
    * comparison := term ( ( ">" | ">=" | "<" | "<=" ) term )*
    **/
    public Expr parseComparison(Scope scope) {
        Expr comp = parseTerm(scope);
        while (peek() == Token.Type.GREATER || peek() == Token.Type.GEQ || peek() == Token.Type.LESS || peek() == Token.Type.LEQ) {
            var token = advance();
            var term = parseTerm(scope);
            comp = new BinaryExpr(token.TYPE, comp, term);
        }
        return comp;
    }

    /**
    * term := factor ( ( "-" | "+" ) factor )*
    **/
    public Expr parseTerm(Scope scope) {
        Expr term = parseFactor(scope);
        while (peek() == Token.Type.MINUS || peek() == Token.Type.PLUS) {
            var token = advance();
            var factor = parseFactor(scope);
            term = new BinaryExpr(token.TYPE, term, factor);
        }
        return term;
    }

    /**
    * factor := exponent ( ( "/" | "*" ) exponent )*
    **/
    public Expr parseFactor(Scope scope) {
        Expr factor = parseExponent(scope);
        while (peek() == Token.Type.TIMES || peek() == Token.Type.DIV) {
            var token = advance();
            var exp = parseExponent(scope);
            factor = new BinaryExpr(token.TYPE, factor, exp);
        }
        return factor;
    }

    /**
     * exponent := unary (^ unary )*
     */
    public Expr parseExponent(Scope scope) {
        Expr exp = parseUnary(scope);
        while (peek() == Token.Type.POW) {
            var token = advance();
            var unary = parseUnary(scope);
            exp = new BinaryExpr(token.TYPE, unary, exp);
        }
        return exp;
    }

    /**
     * unary := := ( "!" | "-" ) unary | primary
     **/
    public Expr parseUnary(Scope scope) {
        Token token;
        switch (peek()) {
            case Token.Type.NOT:
            case Token.Type.MINUS:
                token = advance();
                return new UnaryExpr(token.TYPE, parseUnary(scope));
            default:
                return parsePrimary(scope);
        }
    }

    /**
     * primary := ("true" | "false" | NUMBER | STRING | "(" expression ")" | IDENTIFIER | function_call | array) ([expression])*
     **/
    public Expr parsePrimary(Scope scope) {
        //var token = advance();
        //var type = token.TYPE;
        Expr primary = NullExpr.get();
        var type = peek();
        switch (type) {
            case Token.Type.TRUE: consume(Token.Type.TRUE); primary = new ConstExpr(true); break;
            case Token.Type.FALSE: consume(Token.Type.FALSE); primary = new ConstExpr(false); break;
            case Token.Type.NUM:
            case Token.Type.STR: primary = new ConstExpr(advance().LITERAL); break;
            case Token.Type.ID:
                if (peek(1) == Token.Type.LPAREN) {
                    primary = parseFunctionCall(scope);
                } else {
                    primary = new VarExpr(scope, advance().LEXEME);
                }
                break;
            case Token.Type.LBOXBRACKET: primary = parseArray(scope); break;
            case Token.Type.LPAREN:
                consume(Token.Type.LPAREN);
                primary = parseExpression(scope);
                consume(Token.Type.RPAREN);
                break;
            default:
                System.err.printf("Unexpected Token in Primary Expression: %s\n", type);
                System.exit(1);
        }

        // parse index operation on string, array, identifier or expression
        if (type == Token.Type.ID || type == Token.Type.STR || type == Token.Type.LBOXBRACKET || type == Token.Type.LPAREN) {
            while (match(Token.Type.LBOXBRACKET)) {
                primary = new BinaryExpr(Token.Type.IDX, primary, parseExpression(scope));
                consume(Token.Type.RBOXBRACKET);
            }
        }

        return primary;
    }

    public Expr parseFunctionCall(Scope scope) {
        var idToken = consume(Token.Type.ID);
        consume(Token.Type.LPAREN);
        var args = parseExprList(scope);
        consume(Token.Type.RPAREN);
        return new FuncCallExpr(idToken.LEXEME, args.toArray(new Expr[]{}));
    }

    /**
     * array := "[" "]" | "[" expr_list "]"
     **/
    public ArrayExpr parseArray(Scope scope) {
        consume(Token.Type.LBOXBRACKET);
        if (match(Token.Type.RBOXBRACKET)) {
            return new ArrayExpr(scope, new ArrayList<>()); // empty array
        }   
        var exprs = parseExprList(scope);
        consume(Token.Type.RBOXBRACKET);

        return new ArrayExpr(scope, exprs);
    }

    /**
     * expr_list := expression ("," expression)*
     */
    private ArrayList<Expr> parseExprList(Scope scope) {
        ArrayList<Expr> exprs = new ArrayList<>();
        exprs.add(parseExpression(scope));
        while (match(Token.Type.COMMA)) {
            exprs.add(parseExpression(scope));
        }
        return exprs;
    }

    /**
     * id_list := IDENTIFIER ("," IDENTIFIER)*
     */
    private ArrayList<String> parseIdList() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add(consume(Token.Type.ID).LEXEME);
        while (match(Token.Type.COMMA)) {
            ids.add(consume(Token.Type.ID).LEXEME);
        }
        return ids;
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
