package main;

import tree.Statement;
import utils.NullObj;

public class Function {

    public final String NAME;
    public final String[] ARGSNAMES;
    private Statement seq;
    
    public Function(String name, String[] argsNames, Statement seq) {
        this.NAME = name;
        this.ARGSNAMES = argsNames;
        this.seq = seq;
    }

    public Object eval(Object[] args) {
        if (ARGSNAMES.length != args.length) {
            System.err.printf("Invalid nargs for function %s. Expected %d but got %d\n", NAME, ARGSNAMES.length, args.length);
            return NullObj.get();
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
        scope.setVars(snapshot);

        return res;
    }

    @Override
    public String toString() {
        return String.format("Function %s with arity %d", NAME, ARGSNAMES.length);
    }
}
