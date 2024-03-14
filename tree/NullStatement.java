package tree;

import main.Program;

public class NullStatement extends Statement {
    
    private static NullStatement instance;

    private NullStatement() {
        super();
    }

    @Override
    public void execute(Program context) {
        // Do nothing
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
