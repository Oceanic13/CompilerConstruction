package parser;

import java.util.ArrayList;
import java.util.Arrays;

import scanner.NullToken;
import scanner.Token;

public class Parser {

    private ArrayList<Token> tokens;
    private int index;

    public Parser() {
        this(new ArrayList<>(Arrays. asList(new Token(Token.Type.EOF, "", null, 0))));
    }

    public Parser(ArrayList<Token> tokens) {
        reset(tokens);
    }

    public Parser reset(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        return this;
    }
    
    private Token advance() {
        if (isAtEnd()) return NullToken.get();
        return tokens.get(index++);
    }

    private boolean match(Token.Type expected) {
        if (isAtEnd() || peek() != expected) return false;
        index++;
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
