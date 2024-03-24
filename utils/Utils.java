package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

public class Utils
{

    public static final double EPSILON = 1e-6;
    
    private Utils() {}

    public static <E> boolean contains(E[] arr, E item) {
        return Arrays.stream(arr).anyMatch(item::equals);
    }

    public static <E> int indexOf(E[] arr, E item) {
        for (int i = 0; i < arr.length; ++i) if (arr[i].equals(item)) return i; 
        return -1;
    }

    public static String repeatString(String s, double v) {
        if (v == 0.) return "";
        if (v > 0) {
            String s1 = s.repeat((int)v);
            String s2 = s.substring(0, (int)(s.length()*(v-(int)v)));
            return s1 + s2;
        }
        return new StringBuilder(repeatString(s, -v)).reverse().toString();
    }

    /**
     * Returns the first line index on which there is a brackets mismatch or -1 if there is no mismatch.
     * @param str input text
     * @return line index of brackets mismatch
     */
    public static int findBracketsMismatch(String str) {
        final String BRACKETS = "{}()[]";
        int line = 0;
        Stack<Character> stack = new Stack<>();
        for (char s : str.toCharArray()) {
            if (s == '\n') {line++; continue;}
            for (int i = 0; i < BRACKETS.length()/2; ++i) {
                if (s == BRACKETS.charAt(2*i)) {stack.push(s); break;} //left bracket
                if (s == BRACKETS.charAt(2*i+1)) { // right bracket
                    if (stack.isEmpty() || stack.pop() != BRACKETS.charAt(2*i)) {
                        return line;
                    }
                    break;
                }
            }
        }
        return stack.isEmpty()? -1 : line;
    }

    public static String loadSPL(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <E> String collectionToString(Collection<E> collection, char delimeter) {
        return collectionToString(collection, ""+delimeter);
    }

    public static <E> String collectionToString(Collection<E> collection, String delimeter) {
        StringBuilder b = new StringBuilder();
        for (E e : collection) {
            b.append(e);
            b.append(delimeter);
        }
        return b.toString();
    }
}
