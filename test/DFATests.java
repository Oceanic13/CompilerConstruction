package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scanner.DFA;
import scanner.Token;

public class DFATests {

    @Test
    public void testNumberDFA() {

        DFA dfa = new DFA();
        var s1 = new DFA.State(Token.Type.INTEGER);
        var s2 = new DFA.State(Token.Type.DECIMAL);
        s1.addTransition(DFA.IS_DIGIT, s1);
        s1.addTransition(DFA.IS_CHAR('.'), s2);
        s2.addTransition(DFA.IS_DIGIT, s2);
        dfa.setStart(s1);

        assertEquals(Token.Type.INTEGER, dfa.accept("123"));
        assertEquals(Token.Type.INTEGER, dfa.accept("5001"));
        assertEquals(Token.Type.INTEGER, dfa.accept("290149352473082"));

        assertEquals(Token.Type.DECIMAL, dfa.accept("1."));
        assertEquals(Token.Type.DECIMAL, dfa.accept("42.09"));
        assertEquals(Token.Type.DECIMAL, dfa.accept("2738.37276"));

        assertEquals(Token.Type.NULL, dfa.accept("123a"));
        assertEquals(Token.Type.NULL, dfa.accept("Hello World"));
        assertEquals(Token.Type.NULL, dfa.accept("c(^.^c)"));
    }

    @Test
    public void testLessOrLessEqual() {
        DFA dfa = new DFA();
        var s0 = new DFA.State(Token.Type.NULL);
        s0.addTransition(DFA.IS_CHAR('<'), new DFA.State(Token.Type.LESS)).addTransition(DFA.IS_CHAR('='), new DFA.State(Token.Type.LEQ));
        dfa.setStart(s0);

        assertEquals(Token.Type.LESS, dfa.accept("<"));
        assertEquals(Token.Type.LEQ, dfa.accept("<="));
        assertEquals(Token.Type.NULL, dfa.accept("<==="));
    }
}
