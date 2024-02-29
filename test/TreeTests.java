package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tree.Add;
import tree.IExpr;
import tree.Mult;
import tree.Num;
import utils.Utils;

public class TreeTests {
    
    @Test
    public void testSimpleExpressionTree() {
        IExpr tree = new Add(new Num(5), new Mult(new Num(2), new Num(10)));
        assertEquals(25, tree.eval(), Utils.EPSILON);
    }
}
