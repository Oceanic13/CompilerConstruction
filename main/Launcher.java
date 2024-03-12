package main;

import java.util.ArrayList;

import parser.Parser;
import scanner.Lexer;
import scanner.Token;
import utils.Utils;

public class Launcher {
    
    public static void main(String[] args)
    {
        String raw = Utils.loadSPL("spl/0.spl");
        ArrayList<Token> tokens = new Lexer(raw).tokenize();
        var root = new Parser(tokens).parse();

        System.out.println(Utils.collectionToString(tokens, '\n'));
    }
}
