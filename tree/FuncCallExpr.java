package tree;

import java.util.ArrayList;

import main.Program;
import main.Scope;

public class FuncCallExpr extends Expr {
    
    public FuncCallExpr(Scope scope) {
        super(scope);
        //TODO Auto-generated constructor stub
    }

    private Program func;
    private ArrayList<Expr> args;

    @Override
    public Object eval() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }


}
