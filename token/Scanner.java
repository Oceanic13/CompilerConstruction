package token;

import java.util.ArrayList;

import token.Token.Name;
import utils.IStack;
import utils.Stack;
import utils.Utils;

/**
 * Scanner responsible for lexical analysis to transform a raw input text into a stream of tokens.
 */
public class Scanner {

    private String rawText;
    private ArrayList<Token> tokens;
    private int line;
    private int position;
    private boolean finished, hasError;
    private IStack<Token.Name> bracketsStack;

    public Scanner() {
        this("");
    }

    public Scanner(String text) {
        reset(text);
    }

    public Scanner reset(String text) {
        this.rawText = text;
        this.tokens = new ArrayList<>();
        this.line = 0;
        this.position = 0;
        this.finished = false;
        this.hasError = false;
        this.bracketsStack = new Stack<>();
        return this;
    }
    
    public ArrayList<Token> tokenize() {

        // get all tokens
        while (!isFinished())
            advance();

        // if brackets stack is not empty in the end, we have an unclosed bracket
        if (!bracketsStack.isEmpty()) {
            addErrorToken("Mismatched Brackets");
        }

        // append end of line token if no error has occured
        if (!hasError())
            tokens.add(new Token(Name.EOF, "EOF", null, line));

		return tokens;
    }

    private void advance() {
        
        skipBlanks();
        if (isFinished())
            return;

        Token token = consumeToken();
        updateBracketsStack(token);

        if (!hasError() && token.NAME != Name.COMMENT) {
            tokens.add(token);
        }
    }

    private Token consumeToken() {
        boolean expectString = (rawText.charAt(position) == '\"');

        // Find next token
		int end = -1;
		for (Token.Name t : Token.Name.values()) {
			end = t.endOfMatch(rawText.substring(position));
			if (end != -1) {
				Token token = new Token(t, rawText.substring(position, position + end), null, line);
				position += end;
				return token;
			}
		}

        // No match - Error
        hasError = true;
        if (expectString) return addErrorToken("Unclosed String");
        return addErrorToken("Unexpected Token");
	}

    private void updateBracketsStack(Token currToken) {
        Token.Name s = currToken.NAME;

        // push left bracket
        if (Utils.contains(Token.LBRACKETS, s)) {
            bracketsStack.push(s);
            return;
        }

        // pop bracket if right bracket found, must match
        int i = Utils.indexOf(Token.RBRACKETS, s);
        if (i != -1) {
            if (bracketsStack.isEmpty() || bracketsStack.pop() != Token.LBRACKETS[i]) {
                addErrorToken("Mismatched Brackets");
            }
        }
    }

    private Token addErrorToken(String msg) {
        Token token = new Token(Token.Name.ERROR, String.format("Line %d: %s", line+1, msg), null, line);
        tokens.add(token);
        hasError = true;
        return token;
    }

    private void skipBlanks() {
        while (!isFinished() && Token.BLANKS.contains(rawText.charAt(position))) {
			char c = rawText.charAt(position);
            position++;
			if (c == '\n') line++;
		}
    }

    private boolean isFinished() {
        this.finished = finished || hasError || position >= rawText.length();
        return finished;
    }

    public boolean hasError() {
        return hasError;
    }
}
