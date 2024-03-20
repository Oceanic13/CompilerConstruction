package tree;

import main.Program;
import main.Scope;
import scanner.Token;
import utils.DataType;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    public final Token.Type TYPE;

    public BinaryExpr(Token.Type type, Expr left, Expr right) {
        super();
        this.TYPE = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval() {
        return DataType.apply2(TYPE, left.eval(), right.eval());
    }
    
    @Override
    public String toString() {
        return String.format("<%s %s %s>", left, TYPE, right);
    }
}
