package tree;

import main.Program;

public abstract class Statement extends Node {

    public Statement() {
        super();
    }
    
    public abstract void execute(Program context);

}
