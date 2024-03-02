package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import scanner.Scanner;
import scanner.Token;
import utils.Utils;

public class ScannerTests {

    @Test
    public void testScanner0() {
        String raw = Utils.loadSPL("spl/0.spl");
        Scanner scanner = new Scanner(raw);
        ArrayList<Token> tokens = scanner.tokenize();
        int n = tokens.size();

        assertEquals(Token.Name.VAR, tokens.get(0).NAME);
        assertEquals(Token.Name.EOF, tokens.get(n-1).NAME);

        assertEquals(47, n);

        System.out.println(tokens.toString());
    }

    @Test
    public void testScannerSimple0() {
        Scanner scanner = new Scanner("var b = true;");
        ArrayList<Token> tokens = scanner.tokenize();
        int n = tokens.size();
        assertFalse(scanner.hasError());
        assertEquals(6, n);
        assertEquals(Token.Name.VAR, tokens.get(0).NAME);
        assertEquals(Token.Name.ID, tokens.get(1).NAME);
        assertEquals(Token.Name.ASSIGN, tokens.get(2).NAME);
        assertEquals(Token.Name.TRUE, tokens.get(3).NAME);
        assertEquals(Token.Name.SEMICOLON, tokens.get(4).NAME);
        assertEquals(Token.Name.EOF, tokens.get(5).NAME);
    }

    @Test
    public void testScannerErrors() {
        Scanner scanner = new Scanner();

        // Unexpected Token
        scanner.reset("var a = ?;").tokenize();
        assertTrue(scanner.hasError());

        // Mismatched Brackets
        scanner.reset("if (true) {").tokenize();
        assertTrue(scanner.hasError());

        // Unclosed String
        scanner.reset("var a = \"Hello World;").tokenize();
        assertTrue(scanner.hasError());
    }
}
