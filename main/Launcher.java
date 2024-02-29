package main;

import java.util.ArrayList;

import token.Scanner;
import token.Token;

public class Launcher {
    
    public static void main(String[] args)
    {
        String raw = Utils.loadSPL("spl/print.spl");
        ArrayList<Token> tokens = new Scanner(raw).tokenize();


        System.out.println(tokens.toString());
    }
}
