package parser;

import java.util.ArrayList;
import java.util.Arrays;

import scanner.NullToken;
import scanner.Token;
import scanner.Token.Type;
import tree.Node;
import tree.NullNode;
import utils.Stack;

public class Parser {

    private static final Token.Type[] BRACKETS = {Type.LPAREN, Type.RPAREN, Type.LBRACE, Type.RBRACE, Type.LBOXBRACKET, Type.RBOXBRACKET};

    private ArrayList<Token> tokens;
    private int index;
    private Stack<Token.Type> bracketsStack;

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

    public Node parse() {
        while (!isAtEnd()) {
            int start = index;
        }

        if (!bracketsStack.isEmpty()) {
            System.err.println("Brackets mismatch!");
            System.exit(1);
        }

        return NullNode.get();
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
