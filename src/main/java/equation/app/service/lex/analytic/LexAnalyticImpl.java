package equation.app.service.lex.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LexAnalyticImpl implements LexAnalytic {

    public List<Lexeme> lexAnalyze(String expText) throws WrongEquationException {
        List<Lexeme> lexemes = new ArrayList<>();
        for (int i = 0; i < expText.length(); i++) {
            char c = expText.charAt(i);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.LEFT_BRACKET, c));
                    continue;
                case ')':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.RIGHT_BRACKET, c));
                    continue;
                case '+':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.OP_PLUS, c));
                    continue;
                case '-':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.OP_MINUS, c));
                    continue;
                case '/':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.OP_DIV, c));
                    continue;
                case '*':
                    lexemes.add(new Lexeme(Lexeme.LexemeType.OP_MUL, c));
                    continue;
                default:
                    if ((c >= '0' && c <= '9') || c == 'x' || c == '.') {
                        StringBuilder builder = new StringBuilder();
                        while ((c >= '0' && c <= '9') || c == 'x' || c == '.') {
                            builder.append(c);
                            i++;
                            if (i >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(i);
                        }
                        i--;
                        String digitLexeme = builder.toString();
                        int lengthWithoutDot = digitLexeme.length()
                                - digitLexeme.replace(".", "").length();
                        int lengthWithoutX = digitLexeme.length()
                                - digitLexeme.replace("x", "").length();
                        if (digitLexeme.startsWith(".") || digitLexeme.endsWith(".")
                                || lengthWithoutDot > 1 || lengthWithoutX > 1) {
                            throw new WrongEquationException("Equation isn't correct. "
                                    + "Please, check it.");
                        }
                        lexemes.add(new Lexeme(Lexeme.LexemeType.NUMBER, digitLexeme));
                    } else {
                        throw new WrongEquationException("Equation isn't correct. "
                                + "Unexpected character = " + c);
                    }

            }
        }
        lexemes.add(new Lexeme(Lexeme.LexemeType.EOF, ""));
        return lexemes;
    }

    public List<Lexeme> replaceDigitInsteadX(List<Lexeme> lexemes, BigDecimal x)
            throws WrongEquationException {
        for (Lexeme lexeme : lexemes) {
            String value = lexeme.getValue();
            if (value.contains("x")) {
                if (value.replace("x", "").contains("x")
                        || !value.endsWith("x")) {
                    throw new WrongEquationException("Equation isn't correct. Please, check it.");
                }
                if (value.equals("x")) {
                    lexeme.setValue(value.replace("x", x.toString()));
                    continue;
                }
                BigDecimal temp = new BigDecimal(value.replace("x", ""));
                temp = temp.multiply(x);
                lexeme.setValue(temp.toString());
            }
        }
        return lexemes;
    }
}
