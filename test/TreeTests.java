package test;

import static org.junit.Assert.assertEquals;

import main.TPLProgram;
import org.junit.Test;

import scanner.Token;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.PrintStatement;
import tree.VarDeclExpr;
import tree.VarExpr;
import utils.DataType;

public class TreeTests {

    @Test
    public void testTreeToString1() {
        var program = new TPLProgram();
        var root = new BinaryExpr(Token.Type.PLUS, new ConstExpr(program, 10), new BinaryExpr(Token.Type.TIMES, new ConstExpr(program, 2), new ConstExpr(program, 5)));
        System.out.println(root);
    }

    /*
    @Test
    public void testTreeToString2() {
        // a = a + 1
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.PLUS, new VarExpr(0), new ConstExpr(1)));

        // a < 10
        var cond = new BinaryExpr.Less(new VarExpr(0), new ConstExpr(10));

        // a = 0; while (a < 10) a = a + 1;
        var whileStatement = new WhileStatement(cond, incr);

        System.out.println(whileStatement.toString());
    }
    
    @Test
    public void testSimpleExpressionTree() {
        DataType.init();
        var root = new BinaryExpr(Token.Type.PLUS, new ConstExpr(10), new BinaryExpr(Token.Type.TIMES, new ConstExpr(2), new ConstExpr(5)));
        assertEquals(20, root.eval(null));
    }

    @Test
    public void testSimpleIncrement() {
        DataType.init();

        // a = 0
        var init = new AssignExpr(new VarExpr(0), new ConstExpr(0));

        // a = a + 1
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.PLUS, new VarExpr(0), new ConstExpr(1)));

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
        var incr = new AssignExpr(new VarExpr(0), new BinaryExpr(Token.Type.PLUS, new VarExpr(0), new ConstExpr(1)));

        // a < 10
        var cond = new BinaryExpr.Less(new VarExpr(0), new ConstExpr(10));

        // a = 0; while (a < 10) a = a + 1;
        var whileStatement = new WhileStatement(cond, incr);

        var context = new Program();
        context.addStatement(init);
        context.addStatement(whileStatement);
        context.execute();

        assertEquals(10, context.getVarValue(0));

        System.out.println(context);
    }
*/
    @Test
    public void testSimpleAssignment() throws Exception {
        DataType.init();

        var program = new TPLProgram();

        var root = new VarDeclExpr(new VarExpr(program, "x"), new ConstExpr(program, "Hello World"));
        
        assertEquals("Hello World", root.eval());
        assertEquals("Hello World", program.getScope().readVar("x"));
    }

    @Test
    public void testSimplePrintStatements() throws Exception {
        DataType.init();

        var program = new TPLProgram();

        new PrintStatement(new ConstExpr(program, "Hello World")).eval();
        new PrintStatement(new ConstExpr(program, 0.)).eval();
        new PrintStatement(new ConstExpr(program, 3.141)).eval();
    }
}
