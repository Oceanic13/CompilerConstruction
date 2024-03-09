package tree;

import utils.Data;

public class VarExpr extends Expr {

    protected int varIndex;

    public VarExpr(Program program, int varIndex) {
        super(program);
        this.varIndex = varIndex;
    }

    @Override
    public Data eval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
}
