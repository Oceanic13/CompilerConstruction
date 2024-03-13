package tree;

import main.Context;
import structs.DataValue;

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
    public DataValue<?> eval(Context context) {
        return context.varData(varIndex);
    }
    
}
