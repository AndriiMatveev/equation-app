package equation.app.service;

import equation.app.model.Equation;
import java.util.List;

public interface EquationService {
    void checkAndSaveEquation();

    List<Equation> findAllWithOneAnswer();
}
