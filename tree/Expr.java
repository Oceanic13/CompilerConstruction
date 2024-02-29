package tree;

public abstract class Expr implements IExpr {

    protected IExpr[] children;

    public Expr(IExpr...children) {
        this.children = children;
    }
}
