package test;

import static org.junit.Assert.assertEquals;

import main.Program;

import org.junit.Test;

import scanner.Token;
import tree.AssignExpr;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.PrintStatement;
import tree.VarExpr;
import tree.WhileStatement;
import utils.DataType;

public class TreeTests {

    @Test
    public void testTreeToString1() {
        var root = new BinaryExpr(Token.Type.ADD, new ConstExpr(10), new BinaryExpr(Token.Type.MULT, new ConstExpr(2), new ConstExpr(5)));
        System.out.println(root);
    }

    @Test
    public void testTreeToString2() {
        // a = a + 1
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.ADD, new VarExpr(0), new ConstExpr(1)));

        // a < 10
        var cond = new BinaryExpr.Less(new VarExpr(0), new ConstExpr(10));

        // a = 0; while (a < 10) a = a + 1;
        var whileStatement = new WhileStatement(cond, incr);

        System.out.println(whileStatement.toString());
    }
    
    @Test
    public void testSimpleExpressionTree() {
        DataType.init();
        var root = new BinaryExpr(Token.Type.ADD, new ConstExpr(10), new BinaryExpr(Token.Type.MULT, new ConstExpr(2), new ConstExpr(5)));
        assertEquals(20, root.eval(null));
    }

    @Test
    public void testSimpleIncrement() {
        DataType.init();

        // a = 0
        var init = new AssignExpr(new VarExpr(0), new ConstExpr(0));

        // a = a + 1
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.ADD, new VarExpr(0), new ConstExpr(1)));

        var context = new Program();
        context.addStatement(init);
        context.addStatement(incr);
        context.execute();

        assertEquals(1, context.getVarValue(0));
    }

    @Test
    public void testSimpleWhile() {
        DataType.init();

        // a = 0
        var init = new AssignExpr(new VarExpr(0), new ConstExpr(0));

        // a = a + 1
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.ADD, new VarExpr(0), new ConstExpr(1)));

        // a < 10
        var cond = new BinaryExpr.Less(new VarExpr(0), new ConstExpr(10));

        // a = 0; while (a < 10) a = a + 1;
        var whileStatement = new WhileStatement(cond, incr);

        var context = new Program();
        context.addStatement(init);
        context.addStatement(whileStatement);
        context.execute();

        assertEquals(10, context.getVarValue(0));
    }

    @Test
    public void testSimpleAssignment() {
        DataType.init();

        var root = new AssignExpr(new VarExpr(0), new ConstExpr("Hello World"));
        
        Program context = new Program();
        assertEquals("Hello World", root.eval(context));
        assertEquals("Hello World", context.getVarValue(0));
    }

    @Test
    public void testSimplePrintStatements() {
        DataType.init();

        new PrintStatement(new ConstExpr("Hello World")).execute(null);
        new PrintStatement(new ConstExpr(0)).execute(null);
        new PrintStatement(new ConstExpr(3.141)).execute(null);
    }
}
