package gui;

import javax.swing.JTextArea;

public class Editor extends JTextArea {
    
    public Editor() {
        super();
        setTabSize(2);
        setFont(getFont().deriveFont(32f));
    }

}
