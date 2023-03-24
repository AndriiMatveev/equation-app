package equation.app.service;

import equation.app.model.Answer;
import equation.app.model.Equation;
import equation.app.repository.AnswerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void printAllEquationsByAnswer() {
        System.out.println("Type the answer below" + System.lineSeparator());
        Scanner scanner = new Scanner(System.in);
        try {
            String value = scanner.nextLine();
            List<Equation> equations = findEquations(Double.parseDouble(value));
            equations.forEach(e -> System.out.println(e.getValue()));
        } catch (Exception e) {
            System.out.println("Answer entered incorrectly.");
        }
    }

    private List<Equation> findEquations(Double value) {
        List<Equation> equations = new ArrayList<>();
        Optional<Answer> answerOptional = answerRepository.findByValue(value);
        if (answerOptional.isEmpty()) {
            System.out.println("No such answer found " + value);
            return equations;
        }
        equations.addAll(answerOptional.get().getEquations());
        return equations;
    }
}
