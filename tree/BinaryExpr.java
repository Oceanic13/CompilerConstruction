package tree;

import main.Context;
import scanner.Token;
import utils.DataType;

public class BinaryExpr extends Expr {

    protected Expr left, right;
    protected Token.Type type;

    protected BinaryExpr(Token.Type type, Expr left, Expr right) {
        super();
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(Context context) {
        return DataType.apply2(type, left.eval(context), right.eval(context));
    }
    
}
