package scanner;

import java.util.ArrayList;
import java.util.function.Predicate;

public class DFA {

    public static final Predicate<Character> IS_BLANK = c -> (c == ' ' || c == '\t' || c == '\r');
    public static final Predicate<Character> IS_DIGIT = c -> (c >= '0' && c <= '9');
    public static final Predicate<Character> IS_ALPHA = c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    public static final Predicate<Character> IS_CHAR(char c) {return x -> x==c;}

    private State start;
    
    public DFA() {
        this.start = null;
    }

    public void setStart(State state) {
        this.start = state;
    }

    public Token.Type accept(String s) {
        if (s.isEmpty()) return Token.Type.NULL;
        int index = 0;
        char c = '\0';
        State curr = null;
        State next = start;
        while (index < s.length() && next != null) {
            c = s.charAt(index);
            curr = next;
            next = curr.next(c);
            index++;
        }
        if (next == null) return Token.Type.NULL;

        return next.TYPE;
    }

    public static class State {
        public final Token.Type TYPE;
        private ArrayList<Transition> transitions;
        
        public State(Token.Type type) {
            this.transitions = new ArrayList<>();
            this.TYPE = type;
        }

        public State addTransition(Predicate<Character> p, State to) {
            transitions.add(new Transition(to, p));
            return to;
        }

        public State next(Character c) {
            for (var t : transitions) {
                if (t.PREDICATE.test(c)) {
                    return t.STATE;
                }
            }
            return null;
        }
    }

    public static class Transition {
        public final Predicate<Character> PREDICATE;
        public final State STATE;

        public Transition(State to, Predicate<Character> p) {
            this.PREDICATE = p;
            this.STATE = to;
        }
    }
}
