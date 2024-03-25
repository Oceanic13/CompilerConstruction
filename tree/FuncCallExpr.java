package tree;

import java.util.ArrayList;

import main.Program;
import main.Scope;
import main.Scope.NullScope;

public class FuncCallExpr extends Expr {
    
    public final String NAME;
    public final Expr[] ARGS;

    public FuncCallExpr(String name, Expr...args) {
        super(NullScope.get());
        this.NAME = name;
        this.ARGS = args;
    }

    @Override
    public Object eval() {
        var n = ARGS.length;
        var a = new Object[n];
        for (int i = 0; i<n; ++i) {
            a[i] = ARGS[i].eval();
        }

        // TODO: Func should always define new scope to allow recursion!!!!
        return SCOPE.callFunc(NAME, a);
    }


}
