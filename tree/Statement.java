package tree;

import main.Context;

public abstract class Statement extends Node {

    public Statement() {
        super();
    }
    
    public abstract void execute(Context context);

}
