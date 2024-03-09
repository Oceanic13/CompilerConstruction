package tree;

import utils.Data;

public class AssignExpr extends BinaryExpr {

    public AssignExpr(Program program, VarExpr left, Expr right) {
        super(program, (x,y)->y, left, right);
    }

    @Override
    public Data eval() {
        var r = super.eval();
        PROGRAM.setVarData(((VarExpr)(left)).varIndex(), r);
        return r;
    }
}