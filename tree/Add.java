package tree;
public class Add extends Expr {

    public Add(IExpr left, IExpr right) {
        super(left, right);
    }

    @Override
    public int eval() {
        return children[0].eval() + children[1].eval();
    }

    @Override
    public String toString() {
        return children[0].toString() + " + " + children[1].toString();
    }
}
