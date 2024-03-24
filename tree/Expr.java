package tree;

public abstract class Expr extends Statement {

    public Expr() {
        super();
    }

    public abstract Object eval();

    @Override
    public void execute() {
        // Do nothing
    }
}
