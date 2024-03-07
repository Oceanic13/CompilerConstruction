package scanner;

public class NullToken extends Token {
    private static NullToken instance;

    private NullToken() {
        super(Type.NULL, "", null, -1);
    }

    public static NullToken get() {
        if (instance == null) instance = new NullToken();
        return instance;
    }
}