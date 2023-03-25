package equation.app.service.test.impl;

import equation.app.exception.WrongEquationException;
import equation.app.service.test.EquationTest;
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
        if (equation == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[^\\dx+\\-/*()=.]");
        Matcher matcher = pattern.matcher(equation);
        return !matcher.find();
    }

    private boolean isRightEqualSign(String equation) {
        return !equation.startsWith("=") && !equation.endsWith("=")
                && !equation.replaceFirst("=", "").contains("=");
    }

    private boolean hasRightBrackets(String equation) {
        String temp = equation.replaceAll("[^()]", "");
        Stack<Character> stack = new Stack<>();
        char[] chrs = temp.toCharArray();
        for (char chr : chrs) {
            if (chr == '(') {
                stack.push(chr);
                continue;
            }
            if (chr == ')' && !stack.isEmpty()) {
                if (stack.peek() == '(') {
                    stack.pop();
                    continue;
                }
                return false;
            }
        }
        return stack.isEmpty();
    }
}
