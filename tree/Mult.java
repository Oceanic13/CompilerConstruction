package tree;
public class Mult extends Expr {

    public Mult(IExpr left, IExpr right) {
        super(left, right);
    }

    @Override
    public double eval() {
        return children[0].eval() * children[1].eval();
    }
    
    @Override
    public String toString() {
        return children[0].toString() + " * " + children[1].toString();
    }
}
