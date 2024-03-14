package tree;

import main.Program;

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
    public Object eval(Program context) {
        return context.getVarValue(varIndex);
    }
    
    @Override
    public String toString() {
        return String.format("<VAR:%d>", varIndex);
    }
}
