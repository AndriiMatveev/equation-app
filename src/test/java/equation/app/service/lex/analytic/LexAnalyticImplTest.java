package equation.app.service.lex.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexAnalyticImplTest {
    private final LexAnalyticImpl lexAnalytic = new LexAnalyticImpl();

    @Test
    void lexAnalyze_Ok() {
        List<Lexeme> correctAnswer = List.of(new Lexeme(Lexeme.LexemeType.NUMBER, 'x'),
                new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "5.55"),
                new Lexeme(Lexeme.LexemeType.OP_PLUS, '+'),
                new Lexeme(Lexeme.LexemeType.LEFT_BRACKET, '('),
                new Lexeme(Lexeme.LexemeType.NUMBER, "6x"),
                new Lexeme(Lexeme.LexemeType.OP_MUL, '*'),
                new Lexeme(Lexeme.LexemeType.NUMBER, '6'),
                new Lexeme(Lexeme.LexemeType.RIGHT_BRACKET, ')'),
                new Lexeme(Lexeme.LexemeType.OP_DIV, '/'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "12"),
                new Lexeme(Lexeme.LexemeType.EOF, ""));
        String expression = "x-5.55+(6x*6)/12";
        try {
            List<Lexeme> lexemes = lexAnalytic.lexAnalyze(expression);
            assertArrayEquals(correctAnswer.toArray(),lexemes.toArray());
        } catch (WrongEquationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void lexAnalyze_NotOk() {
        String expression = "x-y+(6x*6)/12";
        Throwable exception = assertThrows(WrongEquationException.class,
                () -> lexAnalytic.lexAnalyze(expression));
        assertNotNull(exception.getMessage());
    }

    @Test
    void replaceDigitInsteadX_Ok() {
        List<Lexeme> lexems = List.of(new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
            new Lexeme(Lexeme.LexemeType.NUMBER, 'x'),
            new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
            new Lexeme(Lexeme.LexemeType.NUMBER, "5.55"),
            new Lexeme(Lexeme.LexemeType.OP_PLUS, '+'),
            new Lexeme(Lexeme.LexemeType.LEFT_BRACKET, '('),
            new Lexeme(Lexeme.LexemeType.NUMBER, "6.5x"),
            new Lexeme(Lexeme.LexemeType.OP_MUL, '*'),
            new Lexeme(Lexeme.LexemeType.NUMBER, '6'),
            new Lexeme(Lexeme.LexemeType.RIGHT_BRACKET, ')'),
            new Lexeme(Lexeme.LexemeType.OP_DIV, '/'),
            new Lexeme(Lexeme.LexemeType.NUMBER, "12"),
            new Lexeme(Lexeme.LexemeType.EOF, ""));
        List<Lexeme> correctAnswer = List.of(new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "2"),
                new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "5.55"),
                new Lexeme(Lexeme.LexemeType.OP_PLUS, '+'),
                new Lexeme(Lexeme.LexemeType.LEFT_BRACKET, '('),
                new Lexeme(Lexeme.LexemeType.NUMBER, "13.0"),
                new Lexeme(Lexeme.LexemeType.OP_MUL, '*'),
                new Lexeme(Lexeme.LexemeType.NUMBER, '6'),
                new Lexeme(Lexeme.LexemeType.RIGHT_BRACKET, ')'),
                new Lexeme(Lexeme.LexemeType.OP_DIV, '/'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "12"),
                new Lexeme(Lexeme.LexemeType.EOF, ""));
        try {
            List<Lexeme> lexemes = lexAnalytic.replaceDigitInsteadX(lexems, new BigDecimal(2));
            assertArrayEquals(correctAnswer.toArray(),lexemes.toArray());
        } catch (WrongEquationException e) {
            throw new RuntimeException(e);
        }
    }
}