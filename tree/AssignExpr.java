package tree;

import scanner.Token;

public class AssignExpr extends BinaryExpr {

    public AssignExpr(VarExpr left, Expr right) {
        super(Token.Type.ASSIGN, left, right);
    }

    @Override
    public Object eval() {
        var r = right.eval();
        var id = (VarExpr)left;
        id.SCOPE.writeVar(id.NAME, r);
        return r;
    }
}