package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import main.Utils;
import token.Scanner;
import token.Token;

public class ScannerTests {

    @Test
    public void testScanner0() {
        String raw = Utils.loadSPL("spl/0.spl");
        Scanner scanner = new Scanner(raw);
        ArrayList<Token> tokens = scanner.tokenize();
        int n = tokens.size();

        assertEquals(Token.Symbol.VAR, tokens.get(0).SYMBOL);
        assertEquals(Token.Symbol.EOF, tokens.get(n-1).SYMBOL);

        assertEquals(47, n);

        System.out.println(tokens.toString());
    }
}
