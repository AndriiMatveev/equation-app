package equation.app.service.test;

import equation.app.exception.WrongEquationException;

public interface EquationTest {
    void doTests(String equation) throws WrongEquationException;
}
