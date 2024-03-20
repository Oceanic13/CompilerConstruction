package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import parser.Parser;
import scanner.Lexer;

public class Window extends JFrame {

    private Editor editor;
    
    public Window() {
        super("Hello World");
        

        JMenuBar mb = new JMenuBar();
        mb.add(initRunButton());
        mb.add(initFileMenu());
        setJMenuBar(mb);

        this.editor = new Editor();
        add(editor);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1080, 920);
    }

    private JButton initRunButton() {
        var runBtn = new JButton("RUN");
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var lexer = new Lexer(editor.getText());
                var parser = new Parser(lexer.tokenize());
                parser.parse().execute();
            }
        });
        return runBtn;
    }

    private JMenu initFileMenu() {
        JMenu fm = new JMenu("File");
        JMenuItem exitmi = new JMenuItem("Exit");
        exitmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }});
        fm.add(exitmi);
        return fm;
    }
}
