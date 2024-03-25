package tree;

import main.Scope;

public abstract class Expr extends Statement {

    public Expr(Scope scope) {
        super(scope);
    }

    public abstract Object eval();

    @Override
    public void execute() {
        // Do nothing
    }
}
