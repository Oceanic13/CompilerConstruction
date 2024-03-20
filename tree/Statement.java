package tree;

import main.Program;
import main.Scope;

public abstract class Statement extends Node {

    public Statement() {
        super();
    }
    
    public abstract void execute();

}
