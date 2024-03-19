package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import main.Program;
import parser.Parser;
import scanner.Lexer;
import scanner.Token;
import tree.BinaryExpr;
import tree.ConstExpr;
import tree.Expr;
import tree.ForStatement;
import tree.IfStatement;
import tree.UnaryExpr;
import utils.DataType;

public class ParserTests {

    @Test
    public void testParsePrimary() {
        var parser = new Parser();
        Expr primary;

        primary = parser.reset(new Token(Token.Type.TRUE, "true", true, 0)).parsePrimary();
        assertTrue(parser.isAtEnd());
        assertEquals(ConstExpr.class, primary.getClass());
        assertEquals(true, primary.eval(null));

        primary = parser.reset(new Token(Token.Type.INT, "123", 123, 0)).parsePrimary();
        assertTrue(parser.isAtEnd());
        assertEquals(ConstExpr.class, primary.getClass());
        assertEquals(123, primary.eval(null));
    }

    @Test
    public void testParseUnary() {
        DataType.init();

        var parser = new Parser();
        Expr unary;

        unary = parser.reset(new Token(Token.Type.TRUE, "true", true, 0)).parseUnary();
        assertTrue(parser.isAtEnd());
        assertEquals(ConstExpr.class, unary.getClass());
        assertEquals(true, unary.eval(null));

        unary = parser.reset(new Lexer().reset("-----7").tokenize()).parseUnary();
        assertTrue(parser.isAtEnd());
        assertEquals(UnaryExpr.class, unary.getClass());
        assertEquals(-7, unary.eval(null));
    }

    @Test
    public void testParseFactor() {
        DataType.init();

        var parser = new Parser();
        Expr factor;

        factor = parser.reset(new Lexer().reset("3 * x / y * 4").tokenize()).parseFactor();
        assertTrue(parser.isAtEnd());
        assertEquals(Token.Type.TIMES, ((BinaryExpr)factor).TYPE);

        System.out.println(factor);

    }

    @Test
    public void testParseTrueOrFalse() {
        DataType.init();

        var parser = new Parser();
        Expr e;

        e = parser.reset(new Lexer().reset("true or false").tokenize()).parseExpression();
        assertTrue(parser.isAtEnd());
        assertEquals(Token.Type.OR, ((BinaryExpr)e).TYPE);

        System.out.println(e);
        
    }

    @Test
    public void testSimpleIf1() {
        DataType.init();

        var parser = new Parser();
        IfStatement ifS;

        ifS = parser.reset(new Lexer().reset("if (true) print \"If is True\"; else print \"If is False\"; ").tokenize()).parseIfStatement();
        assertTrue(parser.isAtEnd());

        ifS.execute(null);

        System.out.println(ifS);
    }

    @Test
    public void testSimpleIf2() {
        DataType.init();

        var parser = new Parser();
        IfStatement ifS;

        ifS = parser.reset(new Lexer().reset("if (true) {print \"If is True\";} else {print \"If is False\";} ").tokenize()).parseIfStatement();
        assertTrue(parser.isAtEnd());

        System.out.println(ifS);
    }

    @Test
    public void testSimpleFor() {
        DataType.init();

        var parser = new Parser();
        ForStatement forS;

        forS = parser.reset(new Lexer().reset("for (var i = 0; i < 10.0; i = i + 1) print i;").tokenize()).parseForStatement();
        assertTrue(parser.isAtEnd());
        new Program(forS).execute();
    }

    @Test
    public void testStringIndex() {
        DataType.init();

        var parser = new Parser();
        Expr e;

        e = parser.reset(new Lexer().reset("var x = \"Hello World\"[6]").tokenize()).parseVarDeclaration();
        assertTrue(parser.isAtEnd());

        var p = new Program(e);
        p.execute();
        assertEquals('W', p.getVarValue(0));
    }

    @Test
    public void testArray() {
        DataType.init();

        var parser = new Parser();
        Expr e;

        e = parser.reset(new Lexer().reset("var a = [1, 10.5, \"Boo\", \'G\', true]").tokenize()).parseVarDeclaration();
        assertTrue(parser.isAtEnd());

        var p = new Program(e);
        p.execute();
        var v = (Object[]) p.getVarValue(0);
    
        assertEquals(1, v[0]);
        assertEquals(10.5, v[1]);
        assertEquals("Boo", v[2]);
        assertEquals('G', v[3]);
        assertEquals(true, v[4]);
    }

    @Test
    public void testPow() {
        DataType.init();

        var parser = new Parser(new Lexer().reset("var a = 2^3^2").tokenize());

        var p = parser.parse();
        assertTrue(parser.isAtEnd());

        p.execute();

        assertEquals(512.0, p.getVarValue(0));
    }
}