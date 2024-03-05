package scanner;

import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;  

import utils.Stack;

/**
 * Scanner responsible for lexical analysis to transform a raw input text into a stream of tokens.
 */
public class Scanner {

    
    private static final Map<String, Token.Type> KEYWORDS = Map.ofEntries(
        entry("true", Token.Type.TRUE),
        entry("false", Token.Type.FALSE),
        entry("var", Token.Type.VAR),
        entry("print", Token.Type.PRINT),
        entry("if", Token.Type.IF),
        entry("else", Token.Type.ELSE),
        entry("while", Token.Type.WHILE)
    );

    private String input;
    private ArrayList<Token> tokens;
    private int line;
    private int index;
    private Stack<Character> bracketsStack;

    public Scanner() {
        this("");
    }

    public Scanner(String text) {
        reset(text);
    }

    public Scanner reset(String text) {
        this.input = text;
        this.tokens = new ArrayList<>();
        this.bracketsStack = new Stack<>();
        this.line = 0;
        this.index = 0;
        return this;
    }
    
    public ArrayList<Token> tokenize() {

        System.out.println(input);

        // get all tokens
        while (!isAtEnd()) {
            int start = index;
            char c = advance();

            switch (c) {
                // whitespace
                case '\r':
                case '\t':
                case ' ': break;

                // newline
                case '\n': line++; break;

                // brackets
                case '{':
                    addToken(Token.Type.LBRACE, c, null);
                    bracketsStack.push('{');
                    break;
                case '}':
                    addToken(Token.Type.RBRACE, c, null);
                    if (bracketsStack.isEmpty() || bracketsStack.pop() != '{') {
                        System.err.println("Mismatched Brackets!");
                        System.exit(1);
                    }
                    break;
                case '(':
                    addToken(Token.Type.LPAREN, c, null);
                    bracketsStack.push('(');
                    break;
                case ')':
                    addToken(Token.Type.RPAREN, c, null);
                    if (bracketsStack.isEmpty() || bracketsStack.pop() != '(') {
                        System.err.println("Mismatched Brackets!");
                        System.exit(1);
                    }
                    break;

                // other single character symbols
                case ';': addToken(Token.Type.SEMICOLON, c, null); break;
                case '+': addToken(Token.Type.ADD, c, null); break;
                case '-': addToken(Token.Type.SUB, c, null); break;
                case '*': addToken(Token.Type.MULT, c, null); break;

                // single character symbols that are prefixes of longer token types
                case '<': if (match('=')) {addToken(Token.Type.LEQ, "<=", null);} else {addToken(Token.Type.LESS, '<', null);} break;
                case '>': if (match('=')) {addToken(Token.Type.GEQ, ">=", null);} else {addToken(Token.Type.GREATER, '>', null);} break;
                case '=': if (match('=')) {addToken(Token.Type.EQ, "==", null);} else {addToken(Token.Type.ASSIGN, '=', null);} break;
                case '!': if (match('=')) {addToken(Token.Type.NEQ, "!=", null);} else {addToken(Token.Type.NOT, '!', null);} break;
                
                case '/': if (match('/')) {while(peek()!='\n'&&!isAtEnd()) advance();} else {addToken(Token.Type.DIV, '/', null);} break;

                // strings
                case '\"':
                    while (peek() != '\"' && !isAtEnd()) {advance();}
                    if (isAtEnd()) {
                        System.err.println("Undeterminated String!");
                        System.exit(1);
                    }
                    advance();
                    String str = input.substring(start+1, index-1);
                    addToken(Token.Type.STR, str, null);
                    break;

                default:
                    // keywords or identifiers
                    if (isAlpha(c)) {
                        while (isAlpha(peek()) || isDigit(peek())) {advance();}
                        String word = input.substring(start, index);

                        Token.Type type = KEYWORDS.getOrDefault(word, Token.Type.ID);
                        addToken(type, word, null);
                        break;
                    }
                    // numbers
                    if (isDigit(c)) {
                        while (isDigit(peek())) {advance();}
                        boolean isDecimal = match('.');
                        if (isDecimal) {while (isDigit(peek())) {advance();}}
                        addToken(isDecimal? Token.Type.DECIMAL : Token.Type.INTEGER, input.substring(start, index), null);
                    }

                    break;
                
            }
        }

        if (!bracketsStack.isEmpty()) {
            System.err.println("Mismatched Brackets!");
            System.exit(1);
        }

        addToken(Token.Type.EOF, '\0', null);
		return tokens;
    }

    private boolean isAlpha(char c) {
        return (c>='a'&&c<='z')||(c>='A'&&c<='Z');
    }

    private boolean isDigit(char c) {
        return (c>='0'&&c<='9');
    }

    private char advance() {
        if (isAtEnd()) return '\0';
        return input.charAt(index++);
    }

    private boolean match(char expected) {
        if (isAtEnd() || peek() != expected) return false;
        index++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return input.charAt(index);
    }

    private boolean isAtEnd() {
        return index >= input.length();
    }

    private void addToken(Token.Type type, char lexeme, Object literal) {
        tokens.add(new Token(type, lexeme, literal, line));
    }

    private void addToken(Token.Type type, String lexeme, Object literal) {
        tokens.add(new Token(type, lexeme, literal, line));
    }
}
