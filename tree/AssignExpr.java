package tree;

import main.Context;
import utils.Data;

public class AssignExpr extends BinaryExpr {

    public AssignExpr(VarExpr left, Expr right) {
        super((x,y)->y, left, right);
    }

    @Override
    public Data eval(Context context) {
        var r = super.eval(context);
        context.setVarData(((VarExpr)(left)).varIndex(), r);
        return r;
    }
}