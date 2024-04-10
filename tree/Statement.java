package tree;

import main.TPLProgram;

public abstract class Statement extends Node {

    public Statement(TPLProgram program) {
        super(program);
    }
    
    public abstract Object eval() throws Exception;

}
