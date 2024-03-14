package tree;

import main.Context;

public class VarExpr extends Expr {

    protected int varIndex;

    public VarExpr(int varIndex) {
        super();
        this.varIndex = varIndex;
    }

    public int varIndex() {
        return varIndex;
    }

    @Override
    public Object eval(Context context) {
        return context.varData(varIndex);
    }
    
}
