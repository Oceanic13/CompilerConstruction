package tree;

import main.Program;

public abstract class Expr extends Statement {

    public Expr() {
    }

    public abstract Object eval(Program context);

    @Override
    public void execute(Program context) {
        // Do nothing
    }
}
