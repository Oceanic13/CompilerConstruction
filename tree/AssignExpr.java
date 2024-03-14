package tree;

import main.Context;
import scanner.Token;

public class AssignExpr extends BinaryExpr {

    public AssignExpr(VarExpr left, Expr right) {
        super(Token.Type.ASSIGN, left, right);
    }

    @Override
    public Object eval(Context context) {
        var r = right.eval(context);
        context.setVarData(((VarExpr)(left)).varIndex(), r);
        return r;
    }
}