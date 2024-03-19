package scanner;

public class Token {
    
    public final String LEXEME;
    public final Token.Type TYPE;
    public final int LINE;
    public final Object LITERAL;

    public Token(Token.Type type, char lexeme, Object literal, int line) {
        this(type, ""+lexeme, literal, line);
    }

    public Token(Token.Type type, String lexeme, Object literal, int line) {
        this.TYPE = type;
        this.LEXEME = lexeme;
        this.LINE = line;
        this.LITERAL = literal;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s> Literal: %s, Line: %d", TYPE, LEXEME, LITERAL, LINE);
    }

    public static enum Type {
        COMMENT,

        LPAREN,
	    RPAREN,
        LBRACE,
	    RBRACE,
        LBOXBRACKET,
        RBOXBRACKET,
        SEMICOLON,
        COMMA,

        PLUS,
        MINUS,
        TIMES,
        DIV,
        POW,
        LEQ,
        GEQ,
        LESS,
        GREATER,
        EQ,
        NEQ,
        OR,
        AND,
        NOT,
        ASSIGN,

        TRUE,
        FALSE,
        VAR,
        PRINT,
        IF,
        ELSE,
        WHILE,
        FOR,
        FUNC,

        //BOOL,
        INT,
        DEC,
        STR,
        CHAR,

        ID,

        IDX,

        NULL,
        EOF;
    }
}
