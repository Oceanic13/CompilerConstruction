package tree;

import scanner.Token.Type;
import utils.DataType;
import utils.ReturnValue;

public class CastExpr<T> extends Expr {

    private Expr child;
    private Class<T> castTo;

    public CastExpr(Class<T> castTo, Expr child) {
        super(child.getScope());
        this.castTo = castTo;
    }

    @Override
    public Object eval() throws Exception {
        var v = child.eval();
        if (v.getClass() == ReturnValue.class) {
            return new ReturnValue(DataType.cast(((ReturnValue)v).VALUE, castTo));
        } else {
            return DataType.cast(v, castTo);
        }
    }
    
}
