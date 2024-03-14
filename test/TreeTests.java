package test;


import static org.junit.Assert.assertEquals;

import main.Context;

import org.junit.Test;

import scanner.Token;
import tree.AssignExpr;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.VarExpr;
import utils.DataType;


public class TreeTests {
    
    @Test
    public void testSimpleExpressionTree() {
        DataType.init();
        var root = new BinaryExpr(Token.Type.ADD, new ConstExpr(10), new BinaryExpr(Token.Type.MULT, new ConstExpr(2), new ConstExpr(5)));
        assertEquals(20, root.eval(null));
    }

    @Test
    public void testSimpleAssignment() {
        DataType.init();
        var root = new AssignExpr(new VarExpr(0), new ConstExpr("Hello World"));
        
        Context context = new Context();
        assertEquals("Hello World", root.eval(context));
        assertEquals("Hello World", context.varData(0));
    }
}
