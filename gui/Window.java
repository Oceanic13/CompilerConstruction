package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import parser.Parser;
import scanner.Lexer;
import utils.Utils;

public class Window extends JFrame {

    private Editor editor;
    private JTextArea tokensLbl;
    private JTextArea treeLbl;
    private Lexer lexer;
    private Parser parser;
    
    public Window() {
        super("Compiler Construction - SPL++");
        this.lexer = new Lexer();
        this.parser = new Parser();
        

        JMenuBar mb = new JMenuBar();
        mb.add(initFileMenu());

        mb.add(Box.createHorizontalGlue());
        mb.add(initRunButton());
        setJMenuBar(mb);

        this.editor = new Editor();
        add(editor);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1080, 920);

        var f = new JFrame("Tokens");
        this.tokensLbl = new JTextArea();
        f.add(tokensLbl);
        f.setSize(320, 480);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setVisible(true);

        f = new JFrame("Tree");
        this.treeLbl = new JTextArea();
        f.add(treeLbl);
        f.setSize(320, 480);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }

    private JButton initRunButton() {
        var runBtn = new JButton("▶");
        runBtn.setToolTipText("Run");
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lexer.reset(editor.getText());
                var tokens = lexer.tokenize();

                tokensLbl.setText(Utils.collectionToString(tokens, '\n'));

                parser.reset(tokens);
                var p = parser.parse();
                treeLbl.setText(p.toString());
                p.execute();
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
