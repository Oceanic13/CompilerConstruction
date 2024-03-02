package parser;

import java.util.ArrayList;

import scanner.Token;
import scanner.Token.Name;
import tree.INode;

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

    public INode parse() {
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
