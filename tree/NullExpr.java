package tree;

import main.Scope;
import utils.NullObj;

public class NullExpr extends Expr {

    private static NullExpr instance;

    private NullExpr() {
        super(Scope.NullScope.get());
    }

    @Override
    public Object eval() {
        return NullObj.get();
    }
    
    public static NullExpr get() {
        if (instance == null) instance = new NullExpr();
        return instance;
    }

    @Override
    public String toString() {
        return "<NULLEXPR>";
    }
}
