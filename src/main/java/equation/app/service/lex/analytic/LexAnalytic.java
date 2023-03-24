package equation.app.service.lex.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import java.util.List;

public interface LexAnalytic {
    List<Lexeme> lexAnalyze(String expText) throws WrongEquationException;

    List<Lexeme> replaceDigitInsteadX(List<Lexeme> lexemes, double x)
            throws WrongEquationException;
}
