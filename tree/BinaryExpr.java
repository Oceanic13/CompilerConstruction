package tree;

import scanner.Token;
import utils.DataType;
import utils.ReturnValue;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    public final Token.Type TYPE;

    public BinaryExpr(Token.Type type, Expr left, Expr right) {
        super(left.getScope());
        this.TYPE = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval() {
        var lt = left.eval();
        var rt = right.eval();
        var l = (lt.getClass()==ReturnValue.class)? ((ReturnValue)lt).VALUE : lt;
        var r = (rt.getClass()==ReturnValue.class)? ((ReturnValue)rt).VALUE : rt;
        return DataType.apply2(TYPE, l, r);
    }
    
    @Override
    public String toString() {
        return String.format("<%s %s %s>", left, TYPE, right);
    }
}
