package tree;

import main.Context;

public class NullStatement extends Statement {
    
    private static NullStatement instance;

    private NullStatement() {
        super();
    }

    @Override
    public void execute(Context context) {
        // Do nothing
    }

    public static NullStatement get() {
        if (instance == null) instance = new NullStatement();
        return instance;
    }
}
