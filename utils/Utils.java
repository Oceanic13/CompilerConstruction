package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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

    public static int findBracketsMismatch(String str)
    {
        int line = 0;
        Stack<Character> stack = new Stack<>();

        for (char s : str.toCharArray()) {
            switch (s) {
                case '\n':
                    line++;
                    break;
                case '[':
                case '{':
                case '(':
                    stack.push(s);
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{')
                        return line;
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[')
                        return line;
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(')
                        return line;
                    break;
                default:
                    break;
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
}
