package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import parser.Parser;
import scanner.Lexer;
import scanner.Token;
import tree.ConstExpr;
import tree.Expr;
import tree.UnaryExpr;
import utils.DataType;
import utils.Utils;

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
}