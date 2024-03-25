package gui;

import java.awt.Color;

import javax.swing.JTextArea;

public class Editor extends JTextArea {
    
    public Editor() {
        super();
        setBackground(Color.darkGray);
        setForeground(Color.green);
        setTabSize(2);
        setFont(getFont().deriveFont(32f));
    }

}
