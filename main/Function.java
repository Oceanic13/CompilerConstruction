package main;

import tree.ReturnStatement;
import tree.Statement;
import tree.VarExpr;
import utils.NullObj;

public class Function implements IFunction {

    public final String NAME;
    public final String[] ARGSNAMES;
    private Statement seq;
    
    public Function(String name, String[] argsNames, Statement seq) {
        this.NAME = name;
        this.ARGSNAMES = argsNames;
        this.seq = seq;
    }

    @Override
    public Object eval(Object[] args) throws Exception {
        if (ARGSNAMES.length != args.length) {
            throw utils.Error.invalidNargsException(NAME, ARGSNAMES.length, args.length);
        }
        int nargs = args.length;

        var scope = seq.getScope();
        assert(scope.isRootScope());

        // Clear scope
        var snapshot = scope.getSnapshot();
        scope.clear();

        // Set argument values
        for (int i = 0; i < nargs; ++i) {
            scope.declVar(ARGSNAMES[i], args[i]);
        }

        // Evaluate function
        var res = seq.eval();

        // Reset scope
        scope.setFromSnapshot(snapshot);

        return res;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", NAME, ARGSNAMES.length);
    }
}
