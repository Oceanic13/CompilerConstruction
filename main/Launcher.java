package main;

import java.util.ArrayList;

import parser.Parser;
import scanner.Scanner;
import scanner.Token;
import utils.Utils;

public class Launcher {
    
    public static void main(String[] args)
    {
        String raw = Utils.loadSPL("spl/0.spl");
        ArrayList<Token> tokens = new Scanner(raw).tokenize();
        var root = new Parser(tokens).parse();

        System.out.println(Utils.collectionToString(tokens, '\n'));
    }
}
