package tree;

import utils.Data;

public class VarExpr extends Expr {

    protected int varIndex;

    public VarExpr(Program program, int varIndex) {
        super(program);
        this.varIndex = varIndex;
    }

    public int varIndex() {
        return varIndex;
    }

    @Override
    public Data eval() {
        return PROGRAM.varData(varIndex);
    }
    
}
