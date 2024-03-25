package tree;

import main.Scope;

public class NullStatement extends Statement {
    
    private static NullStatement instance;

    private NullStatement() {
        super(new Scope());
    }

    @Override
    public void execute() {
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
