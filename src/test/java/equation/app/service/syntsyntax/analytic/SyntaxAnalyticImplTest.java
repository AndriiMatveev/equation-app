package equation.app.service.syntsyntax.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import equation.app.service.lex.analytic.LexemeBuffer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SyntaxAnalyticImplTest {

    @Test
    void expr_Ok() {
        List<Lexeme> lexemes = List.of(new Lexeme(Lexeme.LexemeType.OP_MINUS, '-'),
                new Lexeme(Lexeme.LexemeType.NUMBER, "2.0"),
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
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        BigDecimal correctAnswer = BigDecimal.valueOf(-1.05);
        try {
            assertEquals(correctAnswer, new SyntaxAnalyticImpl(lexemeBuffer).expr(lexemeBuffer));
        } catch (WrongEquationException e) {
            throw new RuntimeException(e);
        }
    }
}