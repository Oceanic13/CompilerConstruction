package scanner;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {

    public static final Set<Character> BLANKS = Set.of('\n', '\r', '\t', ' ');
    public static final Token.Name[] LBRACKETS = {Name.LPAREN, Name.LBOXBRACKET, Name.LBRACE};
    public static final Token.Name[] RBRACKETS = {Name.RPAREN, Name.RBOXBRACKET, Name.RBRACE};
    
    public final String LEXEME;
    public final Token.Name NAME;
    public final int LINE;
    public final Object LITERAL;

    public Token(Token.Name name, String lexeme, Object literal, int line) {
        this.NAME = name;
        this.LEXEME = lexeme;
        this.LINE = line;
        this.LITERAL = literal;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s> Literal: %s, Line: %d", NAME.TYPE, LEXEME, LITERAL, LINE);
    }

    public static enum Type {
        OP, // binary operator
        SPECIAL,
        KEYWORD,
        STR,
        NUM,
        ID,
        COM
    }

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

        FLOAT("\\d*\\.{0,1}\\d+", Type.NUM), // float
        INT("\\d+", Type.NUM), // integer
        STR("\"[^\"]*\"", Type.STR),
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
