package tree;

import main.TPLProgram;

public class VarExpr extends Expr {

    public final String NAME;

    public VarExpr(TPLProgram program, String name) {
        super(program);
        this.NAME = name;
    }

    @Override
    public Object eval() {
        return PROGRAM.getScope().readVar(NAME); // TODO: Eval by reference not by value
    }
    
    @Override
    public String toString() {
        return String.format("<VAR:%s>", NAME);
    }
}
