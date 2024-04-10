package tree;

import utils.DataType;
import utils.NullObj;
import utils.ReturnValue;

public class IteratorStatement extends Statement {
    
    private String iterName;
    private Expr collection;
    private Statement sequence;

    public IteratorStatement(String iterName, Expr collection, Statement seq) {
        super(collection.PROGRAM);
        this.iterName = iterName;
        this.collection = collection;
        this.sequence = seq;
    }

    @Override
    public Object eval() throws Exception {
        var objs = DataType.cast(collection.eval(), DataType.ARRAY_CLASS);

        //System.out.println(Arrays.toString(objs));

        var n = objs.length;
        if (n == 0) return NullObj.get();

        PROGRAM.getScope().declVar(iterName, NullObj.get());

        for (var obj : objs) {
            PROGRAM.getScope().writeVar(iterName, obj);

            var v = sequence.eval();
            if (v.getClass() == ReturnValue.class) return v;
        }
        return NullObj.get();
    }

    
}
