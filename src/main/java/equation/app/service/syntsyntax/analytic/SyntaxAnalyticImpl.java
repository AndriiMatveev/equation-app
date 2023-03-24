package equation.app.service.syntsyntax.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import equation.app.service.lex.analytic.LexemeBuffer;
import org.springframework.stereotype.Component;

@Component
public class SyntaxAnalyticImpl {
    private final LexemeBuffer buffer;

    public SyntaxAnalyticImpl(LexemeBuffer buffer) {
        this.buffer = buffer;
    }

    public double expr(LexemeBuffer buffer) throws WrongEquationException {
        Lexeme lexeme = buffer.next();
        if (lexeme.getType() == Lexeme.LexemeType.EOF) {
            return 0;
        } else {
            buffer.back();
            return plusminus(buffer);
        }
    }

    private double plusminus(LexemeBuffer buffer) throws WrongEquationException {
        double value = multdiv(buffer);
        while (true) {
            Lexeme lexeme = buffer.next();
            switch (lexeme.getType()) {
                case OP_PLUS:
                    value += multdiv(buffer);
                    break;
                case OP_MINUS:
                    value -= multdiv(buffer);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                    buffer.back();
                    return value;
                default:
                    throw new WrongEquationException("Unexpected token: " + lexeme.getValue()
                            + " at position: " + buffer.getPosition());
            }
        }
    }

    private double multdiv(LexemeBuffer buffer) throws WrongEquationException {
        double value = factor(buffer);
        while (true) {
            Lexeme lexeme = buffer.next();
            switch (lexeme.getType()) {
                case OP_MUL:
                    value *= factor(buffer);
                    break;
                case OP_DIV:
                    value /= factor(buffer);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    buffer.back();
                    return value;
                default:
                    throw new WrongEquationException("Unexpected token: " + lexeme.getValue()
                            + " at position: " + buffer.getPosition());
            }
        }
    }

    private double factor(LexemeBuffer buffer) throws WrongEquationException {
        Lexeme lexeme = buffer.next();
        switch (lexeme.getType()) {
            case OP_MINUS:
                double value = factor(buffer);
                return -value;
            case NUMBER:
                return Double.parseDouble(lexeme.getValue());
            case LEFT_BRACKET:
                value = expr(buffer);
                lexeme = buffer.next();
                if (lexeme.getType() != Lexeme.LexemeType.RIGHT_BRACKET) {
                    throw new WrongEquationException("Equation isn't correct. "
                            + "Unexpected character at " + buffer.getPosition());
                }
                return value;
            default:
                throw new WrongEquationException("Equation isn't correct. "
                        + "Unexpected character at " + buffer.getPosition());
        }
    }
}
