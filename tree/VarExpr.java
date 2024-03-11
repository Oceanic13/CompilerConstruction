package tree;

import main.Context;
import utils.Data;

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
    public Data eval(Context context) {
        return context.varData(varIndex);
    }
    
}
