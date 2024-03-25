package tree;

import scanner.Token;

public class VarDeclExpr extends BinaryExpr {

    public VarDeclExpr(VarExpr left, Expr right) {
        super(Token.Type.ASSIGN, left, right);
    }

    @Override
    public Object eval() {
        var r = right.eval();
        var id = (VarExpr)left;
        id.SCOPE.declVar(id.NAME, r);
        return r;
    }

    @Override
    public String toString() {
        return String.format("<%s %s %s>", left, "DECLARE", right);
    }
}