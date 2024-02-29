package token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
    
    public final String LEXEME;
    public final Token.Symbol SYMBOL;
    public final int LINE;
    public final Object LITERAL;

    public Token(Token.Symbol symbol, String lexeme, Object literal, int line) {
        this.SYMBOL = symbol;
        this.LEXEME = lexeme;
        this.LINE = line;
        this.LITERAL = literal;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s> Literal: %s, Line: %d", SYMBOL.TYPE, LEXEME, LITERAL, LINE);
    }

    public static enum Type {
        OPERATOR,
        SPECIAL,
        KEYWORD,
        STRING,
        NUMBER,
        IDENTIFIER,
        NULL
    }

    public static enum Symbol {

        COMMENT("//[^\n]*", Type.NULL), // single line

        LPAREN("\\(", Type.SPECIAL),
	    RPAREN("\\)", Type.SPECIAL),
	    LBOXBRACKET("\\[", Type.SPECIAL),
	    RBOXBRACKET("\\]", Type.SPECIAL),
        LBRACE("\\{", Type.SPECIAL),
	    RBRACE("\\}", Type.SPECIAL),
        SEMICOLON(";", Type.SPECIAL),
        
        LEQ("<=", Type.OPERATOR),
        GEQ(">=", Type.OPERATOR),
        LESS("<", Type.OPERATOR),
        GREATER(">", Type.OPERATOR),
        ADD("\\+", Type.OPERATOR),
        SUB("-", Type.OPERATOR),
        MULT("\\*", Type.OPERATOR),
        DIV("/", Type.OPERATOR),
        EQ("==", Type.OPERATOR),
        NEQ("!=", Type.OPERATOR),
        OR("or|\\|\\|", Type.OPERATOR),
        AND("and|&&", Type.OPERATOR),
        NOT("!|not", Type.OPERATOR),
        ASSIGN("=", Type.OPERATOR),

        TRUE("true", Type.KEYWORD),
        FALSE("false", Type.KEYWORD),
        VAR("var", Type.KEYWORD),
        PRINT("print", Type.KEYWORD),
        IF("if", Type.KEYWORD),
        ELSE("else", Type.KEYWORD),
        WHILE("while", Type.KEYWORD),

        NUM("\\d*\\.{0,1}\\d+", Type.NUMBER), // integer or decimal
        STR("\"[^\"]*\"", Type.STRING),
        ID("\\w+", Type.IDENTIFIER), // identifier

        EOF("\\A(?!x)x", Type.NULL), // end of file
        ERROR("\\A(?!x)x", Type.NULL);
    
        public final Pattern PATTERN;
        public final Token.Type TYPE;

        private Symbol(String regex, Token.Type type) {
            this.PATTERN = Pattern.compile("^(?i)" + regex);
            // the ^ requires the regex to match at the start of the string, for efficiency
            // the (?i) makes our language case insensitive
            this.TYPE = type;
        }

        /**
         * Returns the first character position in the given String
         * it no longer matches the pattern of the Token or -1 if they don't match
         * at all or the start of the match is not the first index.
         * @param str String to match
         * @return End of Match
         */
        public int endOfMatch(String str) {
            Matcher matcher = PATTERN.matcher(str);
            return matcher.find()&&matcher.start()==0? matcher.end() : -1;
        }
    }

    
}
