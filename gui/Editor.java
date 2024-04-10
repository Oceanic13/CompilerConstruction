package gui;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Editor extends JScrollPane {

    JTextPane textPane;
    
    public Editor() {
        super();
        setBackground(Color.darkGray);
        setForeground(Color.lightGray);

        this.textPane = new JTextPane();
        textPane.setFont(getFont().deriveFont(24f));
        this.setViewportView(textPane);
        this.setRowHeaderView(new TextLineNumber(textPane));
    }

    public void setText(String text) {
        textPane.setText(text);
    }

    public String getText() {
        return textPane.getText();
    }

}
