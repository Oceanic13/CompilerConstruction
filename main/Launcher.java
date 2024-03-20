package main;

import java.util.ArrayList;

import gui.Window;
import parser.Parser;
import scanner.Lexer;
import scanner.Token;
import utils.DataType;
import utils.Utils;

public class Launcher {
    
    public static void main(String[] args) {
        DataType.init();

        String raw = Utils.loadSPL("spl/print.spl");
        ArrayList<Token> tokens = new Lexer(raw).tokenize();
        System.out.println(Utils.collectionToString(tokens, '\n'));
        
        var p = new Parser(tokens).parse();
        System.out.println(p);

        p.execute();

        new Window();
    }
}
