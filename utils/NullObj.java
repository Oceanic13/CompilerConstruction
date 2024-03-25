package utils;

public class NullObj {
    
    private static NullObj instance;

    private NullObj() {}

    public static NullObj get() {
        if (instance==null) instance=new NullObj();
        return instance;
    }

    @Override
    public String toString() {
        return "NULL";
    }
}
