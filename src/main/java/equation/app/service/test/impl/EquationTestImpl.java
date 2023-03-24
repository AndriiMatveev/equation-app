package equation.app.service.test.impl;

import equation.app.exception.WrongEquationException;
import equation.app.service.test.EquationTest;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class EquationTestImpl implements EquationTest {
    @Override
    public void doTests(String equation) throws WrongEquationException {
        if (isRightCharacters(equation) && hasRightBrackets(equation)
                && isRightEqualSign(equation)) {
            return;
        }
        throw new WrongEquationException("Equation isn't correct. Please, check it.");
    }

    private boolean isRightCharacters(String equation) {
        Pattern pattern = Pattern.compile("^[\\dx+\\-/*()=.]");
        Matcher matcher = pattern.matcher(equation);
        return matcher.find();
    }

    private boolean isRightEqualSign(String equation) {
        int temp = equation.length()
                - equation.replace("=", "").length();
        if (!equation.startsWith("=") && !equation.startsWith("=")
                && !equation.replace("=", "").contains("=")) {
            return true;
        }
        return false;
    }

    private boolean hasRightBrackets(String equation) {
        String temp = equation.replaceAll("[^()]", "");
        Stack<Character> stack = new Stack<>();
        HashMap<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        char[] chrs = temp.toCharArray();
        for (char chr : chrs) {
            if (map.containsValue(chr)) {
                stack.push(chr);
                continue;
            }
            if (map.containsKey(chr) && !stack.isEmpty()) {
                if (map.get(chr).equals(stack.peek())) {
                    stack.pop();
                    continue;
                }
                return false;
            }
        }
        return stack.isEmpty();
    }
}
