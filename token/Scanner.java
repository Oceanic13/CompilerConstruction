package token;

import java.util.ArrayList;
import java.util.Set;

import token.Token.Symbol;
import utils.IStack;
import utils.Stack;
import utils.Utils;

public class Scanner {

    private String rawText;
    private ArrayList<Token> tokens;
    private int line, position;
    private boolean finished, hasError;
    private static final Set<Character> BLANKS = Set.of('\n', '\r', '\t', ' ');

    private IStack<Token.Symbol> brackets;
    private static final Token.Symbol[] LBRACKETS = {Symbol.LPAREN, Symbol.LBOXBRACKET, Symbol.LBRACE};
    private static final Token.Symbol[] RBRACKETS = {Symbol.RPAREN, Symbol.RBOXBRACKET, Symbol.RBRACE};

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
        this.brackets = new Stack<>();
    }
    
    public ArrayList<Token> tokenize() {
        while (!isFinished())
            advance();

        if (!brackets.isEmpty()) {
            addErrorToken("Mismatched Brackets");
        }

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
        updateBracketsStack(token);

        if (token.SYMBOL != Symbol.COMMENT) {
            tokens.add(token);
        }
    }

    private Token getToken() {
        boolean expectString = (rawText.charAt(position) == '\"');

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

        // No match - Error
        hasError = true;

        String msg = expectString?
        String.format("Unclosed String on line %d.", line) :
        String.format("Unexpected Token on line %d.", line);

		return new Token(Symbol.ERROR, msg, null, line);
	}

    private void updateBracketsStack(Token currToken) {
        Token.Symbol s = currToken.SYMBOL;

        // push left bracket
        if (Utils.contains(LBRACKETS, s)) {
            brackets.push(s);
            return;
        }

        // pop bracket if right bracket found, must match
        int i = Utils.indexOf(RBRACKETS, s);
        if (i != -1) {
            if (brackets.isEmpty() || brackets.pop() != LBRACKETS[i]) {
                addErrorToken("Mismatched Brackets");
            }
        }
    }

    private void addErrorToken(String msg) {
        tokens.add(new Token(Token.Symbol.ERROR, String.format("Line %d: %s", line+1, msg), null, line));
        hasError = true;
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
