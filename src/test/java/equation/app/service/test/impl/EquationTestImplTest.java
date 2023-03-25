package equation.app.service.test.impl;

import equation.app.exception.WrongEquationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquationTestImplTest {
    private EquationTestImpl equationTest = new EquationTestImpl();

    @Test
    void doTests_Ok() {
        String example = "x-(-2x+6.57*(x+3))=6x";
        try {
            equationTest.doTests(example);
        } catch (WrongEquationException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void doTests_WrongCharacter_NotOk() throws WrongEquationException {
        String example = "x-(-2yx+6.57*(x+3))=6x";
        Throwable exception = assertThrows(WrongEquationException.class,
                () -> equationTest.doTests(example));
        assertNotNull(exception.getMessage());
    }

    @Test
    void doTests_WrongBrackets_NotOk() throws WrongEquationException {
        String example = "x-(-2x+(6.57*(x+3))=6x";
        Throwable exeption = assertThrows(WrongEquationException.class,
                () -> equationTest.doTests(example));
        assertNotNull(exeption.getMessage());
    }

    @Test
    void doTests_EqualSign_NotOk() throws WrongEquationException {
        String example = "x-(-2x+6.57*(x+3))==6x";
        Throwable exeption = assertThrows(WrongEquationException.class,
                () -> equationTest.doTests(example));
        assertNotNull(exeption.getMessage());
    }

    @Test
    void doTests_Null_NotOk() throws WrongEquationException {
        Throwable exeption = assertThrows(WrongEquationException.class,
                () -> equationTest.doTests(null));
        assertNotNull(exeption.getMessage());
    }
}