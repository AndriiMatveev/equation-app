package equation.app.service.syntsyntax.analytic;

import equation.app.exception.WrongEquationException;
import equation.app.model.lexeme.Lexeme;
import equation.app.service.lex.analytic.LexemeBuffer;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class SyntaxAnalyticImpl {
    private final LexemeBuffer buffer;

    public SyntaxAnalyticImpl(LexemeBuffer buffer) {
        this.buffer = buffer;
    }

    public BigDecimal expr(LexemeBuffer buffer) throws WrongEquationException {
        return plusminus(buffer);
    }

    private BigDecimal plusminus(LexemeBuffer buffer) throws WrongEquationException {
        BigDecimal value = multdiv(buffer);
        while (true) {
            Lexeme lexeme = buffer.next();
            switch (lexeme.getType()) {
                case OP_PLUS:
                    value = value.add(multdiv(buffer));
                    break;
                case OP_MINUS:
                    value = value.subtract(multdiv(buffer));
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

    private BigDecimal multdiv(LexemeBuffer buffer) throws WrongEquationException {
        BigDecimal value = factor(buffer);
        while (true) {
            Lexeme lexeme = buffer.next();
            switch (lexeme.getType()) {
                case OP_MUL:
                    value = value.multiply(factor(buffer));
                    break;
                case OP_DIV:
                    value = value.divide(factor(buffer));
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    buffer.back();
                    return value;
                default:
                    throw new WrongEquationException("Unexpected character: "
                            + lexeme.getValue()
                            + " at position: " + buffer.getPosition());
            }
        }
    }

    private BigDecimal factor(LexemeBuffer buffer) throws WrongEquationException {
        Lexeme lexeme = buffer.next();
        switch (lexeme.getType()) {
            case OP_MINUS:
                BigDecimal value = factor(buffer);
                return value.negate();
            case NUMBER:
                return new BigDecimal(lexeme.getValue());
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
