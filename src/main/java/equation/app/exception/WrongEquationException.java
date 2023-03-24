package equation.app.exception;

import java.io.IOException;

public class WrongEquationException extends IOException {
    public WrongEquationException(String message) {
        super(message);
    }
}
