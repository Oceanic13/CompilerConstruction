package main;

import gui.Window;
import utils.DataType;

public class Launcher {
    
    public static void main(String[] args) {
        DataType.init();
        new Window();
    }
}
