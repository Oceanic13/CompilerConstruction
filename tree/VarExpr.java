package tree;

import main.Context;
import structs.DataType;

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
    public DataType eval(Context context) {
        return context.varData(varIndex);
    }
    
}
