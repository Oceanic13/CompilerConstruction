package token;

import java.util.ArrayList;
import java.util.Set;

import token.Token.Symbol;

public class Scanner {

    private String rawText;
    private ArrayList<Token> tokens;
    private int line, position;
    private boolean finished, hasError;
    private static final Set<Character> BLANKS = Set.of('\n', '\r', '\t', ' ');

    public Scanner(String text) {
        reset(text);
    }

    public void reset(String text) {
        this.rawText = text;
        this.tokens = new ArrayList<>();
        this.line = 0;
        this.position = 0;
        this.finished = false;
        this.hasError = false;
    }
    
    public ArrayList<Token> tokenize() {
        while (!isFinished())
            advance();

        // append end of line token if no error has occured
        if (!hasError())
            tokens.add(new Token(Symbol.EOF, "EOF", null, line));

		return tokens;
    }

    private void advance() {
        
        skipBlanks();
        if (isFinished())
            return;

        Token token = getToken();
        if (token.SYMBOL != Symbol.COMMENT) // ignore comments
            tokens.add(token);
    }

    private Token getToken() {

        // Find next token
		int end = -1;
		for (Token.Symbol t : Token.Symbol.values()) {
			end = t.endOfMatch(rawText.substring(position));
			if (end != -1) {
				Token token = new Token(t, rawText.substring(position, position + end), null, line);
				position += end;
				return token;
			}
		}

        // Unexpected token
        hasError = true;
		return new Token(Symbol.ERROR, String.format("Unexpected Token on line %d.", line), null, line);
	}

    private void skipBlanks() {
        while (!isFinished() && BLANKS.contains(rawText.charAt(position))) {
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
