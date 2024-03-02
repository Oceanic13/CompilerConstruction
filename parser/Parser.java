package parser;

import java.util.ArrayList;

import token.Token;
import token.Token.Name;

public class Parser {
    
    // P = program, S = statement, E = expression
    // P = S*
    // IFS = if (E) {P} optional: else {P}
    // WHILES = while (E) {P}
    // E = NUMBER | IDENTIFIER | E BINOP E | PREOP E | E POSTOP

    private ArrayList<Token> tokens;
    private int currIndex;

    public Parser(ArrayList<Token> tokens) {
        reset(tokens);
    }

    public void reset(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currIndex = 0;
    }

    private void parseInstructions() {

    }

    private void parseInstruction() {
        switch (peek().NAME) {
            case IF: parseIf(); break;
            case WHILE: parseWhile(); break;
            case VAR: parseAssignment(); break;
            default:
                break;
        }
    }

    private void parseExpression() {

    }

    // var ID = EXPR
    private void parseAssignment() {
        eat(Name.VAR);
        eat(Name.ID);
        eat(Name.ASSIGN);
        parseExpression();
        eat(Name.SEMICOLON);
    }

    // IF (EXPR) {INSTRUCTIONS}
    // optional: ELSE {INSTRUCTIONS}
    private void parseIf() {
        eat(Name.IF);
        eat(Name.LPAREN);
        parseExpression();
        eat(Name.RPAREN);
        eat(Name.LBRACE);
        parseInstructions();
        eat(Name.RBRACE);
        if (is(Name.ELSE)) {
            eat(Name.LBRACE);
            parseInstructions();
            eat(Name.RBRACE);
        }

    }

    private void parseWhile() {
        eat(Name.WHILE);
        eat(Name.LPAREN);
        parseExpression();
        eat(Name.RPAREN);
        eat(Name.LBRACE);
        parseInstructions();
        eat(Name.RBRACE);
    }

    private boolean is(Token.Name name) {
        return peek().NAME == name;
    }

    private Token peek() {
        return tokens.get(currIndex);
    }

    private Token eat(Token.Name expected) {
        assert(peek().NAME == expected);
        Token t = peek();
        currIndex++;
        return t;
    }
}
