package tree;

import main.Program;
import scanner.Token;

public class AssignExpr extends BinaryExpr {

    public AssignExpr(VarExpr left, Expr right) {
        super(Token.Type.ASSIGN, left, right);
    }

    @Override
    public Object eval(Program context) {
        var r = right.eval(context);
        context.setVarValue(((VarExpr)(left)).varIndex(), r);
        return r;
    }

    @Override
    public void execute(Program context) {
        eval(context);
    }
}