package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils
{
    
    private Utils() {}

    public static boolean hasBalancedBrackets(String str)
    {
        Stack<Character> stack = new Stack<>();

        for (char s : str.toCharArray()) {
            switch (s) {
                case '[':
                case '{':
                case '(':
                    stack.push(s);
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{')
                        return false;
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[')
                        return false;
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(')
                        return false;
                    break;
                default:
                    break;
            }
        }
        return stack.isEmpty();
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
