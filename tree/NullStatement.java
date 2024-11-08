package tree;

import utils.NullObj;

public class NullStatement extends Statement {
    
    private static NullStatement instance;

    private NullStatement() {
        super(null);
    }

    @Override
    public Object eval() {
        return NullObj.get();
    }

    public static NullStatement get() {
        if (instance == null) instance = new NullStatement();
        return instance;
    }

    @Override
    public String toString() {
        return "<NULLSTATEMENT>";
    }
}
