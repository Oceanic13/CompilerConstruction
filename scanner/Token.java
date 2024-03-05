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
	    //LBOXBRACKET,
	    //RBOXBRACKET,
        LBRACE,
	    RBRACE,
        SEMICOLON,
        
        ADD,
        SUB,
        MULT,
        DIV,

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

        INTEGER, // integer
        DECIMAL,
        STR, // string
        ID, // identifier

        EOF, // end of file
        ERROR;
    }

    /*
    @Deprecated
    public static enum Name {

        COMMENT("//[^\n]*", Type.COM), // single line

        LPAREN("\\(", Type.SPECIAL),
	    RPAREN("\\)", Type.SPECIAL),
	    LBOXBRACKET("\\[", Type.SPECIAL),
	    RBOXBRACKET("\\]", Type.SPECIAL),
        LBRACE("\\{", Type.SPECIAL),
	    RBRACE("\\}", Type.SPECIAL),
        SEMICOLON(";", Type.SPECIAL),
        
        ADD("\\+", Type.OP),
        SUB("-", Type.OP),
        MULT("\\*", Type.OP),
        DIV("/", Type.OP),

        LEQ("<=", Type.OP),
        GEQ(">=", Type.OP),
        LESS("<", Type.OP),
        GREATER(">", Type.OP),
        EQ("==", Type.OP),
        NEQ("!=", Type.OP),
        OR("or|\\|\\|", Type.OP),
        AND("and|&&", Type.OP),
        NOT("!|not", Type.OP),

        ASSIGN("=", Type.OP),

        TRUE("true", Type.KEYWORD),
        FALSE("false", Type.KEYWORD),
        VAR("var", Type.KEYWORD),
        PRINT("print", Type.KEYWORD),
        IF("if", Type.KEYWORD),
        ELSE("else", Type.KEYWORD),
        WHILE("while", Type.KEYWORD),

        INT("\\d+", Type.NUM), // integer
        FLOAT("\\d*\\.{0,1}\\d+", Type.NUM), // float
        STR("\"[^\"]*\"", Type.STR), // string
        ID("\\w+", Type.ID), // identifier

        EOF("\\A(?!x)x", Type.COM), // end of file
        ERROR("\\A(?!x)x", Type.COM);
    
        public final Pattern PATTERN;
        public final Token.Type TYPE;

        private Name(String regex, Token.Type type) {
            this.PATTERN = Pattern.compile("^(?i)" + regex);
            // the ^ requires the regex to match at the start of the string, for efficiency
            // the (?i) makes our language case insensitive
            this.TYPE = type;
        }

        public int endOfMatch(String str) {
            Matcher matcher = PATTERN.matcher(str);
            if (!matcher.find()) return -1;
            assert(matcher.start() == 0);
            return matcher.end();
        }
    }
    */
    
}
