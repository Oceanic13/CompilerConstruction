package tree;

import main.Context;

public abstract class Expr extends Statement {

    public Expr() {
    }

    public abstract Object eval(Context context);

    @Override
    public void execute(Context context) {
        // Do nothing
    }
}
