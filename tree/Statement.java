package tree;

import main.Scope;

public abstract class Statement extends Node {

    private Scope scope;

    public Statement(Scope scope) {
        super();
        this.scope = scope;
    }
    
    public abstract Object eval() throws Exception;

    //public void setScope(Scope scope) { this.scope = scope;}

    public Scope getScope() {return scope;}

}
