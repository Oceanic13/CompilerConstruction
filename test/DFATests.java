package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scanner.DFA;
import scanner.Token;
import utils.Pair;

public class DFATests {

    public DFA DFA_Number() {
        DFA dfa = new DFA();
        var s0 = new DFA.State(Token.Type.NULL);
        var s1 = new DFA.State(Token.Type.INTEGER);
        var s2 = new DFA.State(Token.Type.DECIMAL);
        s0.addTransition(DFA.IS_DIGIT, s1);
        s1.addTransition(DFA.IS_DIGIT, s1);
        s1.addTransition(DFA.IS_CHAR('.'), s2);
        s2.addTransition(DFA.IS_DIGIT, s2);
        dfa.setStart(s0);
        return dfa;
    }

    @Test
    public void testNumberDFAWholeInteger() {
        var dfa = DFA_Number();
        assertEquals(new Pair<>(Token.Type.INTEGER, 3), dfa.endOfMatch("123"));
        assertEquals(new Pair<>(Token.Type.INTEGER, 4), dfa.endOfMatch("5001"));
        assertEquals(new Pair<>(Token.Type.INTEGER, 15), dfa.endOfMatch("290149352473082"));
    }

    @Test
    public void testNumberDFAWholeDecimal() {
        var dfa = DFA_Number();
        assertEquals(new Pair<>(Token.Type.DECIMAL, 2), dfa.endOfMatch("1."));
        assertEquals(new Pair<>(Token.Type.DECIMAL, 5), dfa.endOfMatch("42.09"));
        assertEquals(new Pair<>(Token.Type.DECIMAL, 10), dfa.endOfMatch("2738.37276"));
    }

    @Test
    public void testNumberDFAPrefixInteger() {
        var dfa = DFA_Number();
        assertEquals(new Pair<>(Token.Type.INTEGER, 3), dfa.endOfMatch("123abc"));
    }

    @Test
    public void testNumberDFAPrefixDecimal() {
        var dfa = DFA_Number();
        assertEquals(new Pair<>(Token.Type.DECIMAL, 4), dfa.endOfMatch("12.3a4bc"));
    }

    @Test
    public void testNumberDFAMismatch() {
        var dfa = DFA_Number();
        assertEquals(new Pair<>(Token.Type.NULL, 0), dfa.endOfMatch("Hello World"));
        assertEquals(new Pair<>(Token.Type.NULL, 0), dfa.endOfMatch("c(^.^c)"));
    }
}
