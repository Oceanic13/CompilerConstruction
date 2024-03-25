package tree;

import main.Scope;

public abstract class Statement extends Node {

    public final Scope SCOPE;

    public Statement(Scope scope) {
        super();
        this.SCOPE = scope;
    }
    
    public abstract Object eval();

}
