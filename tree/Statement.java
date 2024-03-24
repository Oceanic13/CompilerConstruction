package tree;

public abstract class Statement extends Node {

    public Statement() {
        super();
    }
    
    public abstract void execute();

}
