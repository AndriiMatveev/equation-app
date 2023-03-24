package equation.app.service;

import equation.app.exception.WrongEquationException;
import equation.app.model.Answer;
import equation.app.model.Equation;
import equation.app.model.lexeme.Lexeme;
import equation.app.repository.AnswerRepository;
import equation.app.repository.EquationRepository;
import equation.app.service.lex.analytic.LexAnalytic;
import equation.app.service.lex.analytic.LexemeBuffer;
import equation.app.service.syntsyntax.analytic.SyntaxAnalyticImpl;
import equation.app.service.test.EquationTest;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class EquationServiceImpl implements EquationService {
    private final LexAnalytic lexAnalytic;
    private final LexemeBuffer lexemeBuffer;
    private final EquationTest equationTest;
    private final SyntaxAnalyticImpl syntaxAnalytic;
    private final EquationRepository equationRepository;
    private final AnswerRepository answerRepository;

    public EquationServiceImpl(LexAnalytic lexAnalytic, LexemeBuffer lexemeBuffer,
                               EquationTest equationTest, SyntaxAnalyticImpl syntaxAnalytic,
                               EquationRepository equationRepository,
                               AnswerRepository answerRepository) {
        this.lexAnalytic = lexAnalytic;
        this.lexemeBuffer = lexemeBuffer;
        this.equationTest = equationTest;
        this.syntaxAnalytic = syntaxAnalytic;
        this.equationRepository = equationRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void checkAndSaveEquation() {
        System.out.println("Type the equation below" + System.lineSeparator());
        Scanner scanner = new Scanner(System.in);
        String equation = scanner.nextLine();
        try {
            equationTest.doTests(equation);
        } catch (WrongEquationException e) {
            System.out.println("Equation entered incorrectly.");
            return;
        }
        System.out.println("Type the answer below" + System.lineSeparator());
        double answer;
        try {
            answer = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Answer entered incorrectly.");
            return;
        }
        String[] partsOfEquation = equation.split("=");
        try {
            List<Lexeme> leftPartLexemes = lexAnalytic.lexAnalyze(partsOfEquation[0]);
            leftPartLexemes = lexAnalytic.replaceDigitInsteadX(leftPartLexemes, answer);
            lexemeBuffer.setPosition(0);
            lexemeBuffer.setLexemes(leftPartLexemes);
            List<Lexeme> rightPartLexemes = lexAnalytic.lexAnalyze(partsOfEquation[1]);
            rightPartLexemes = lexAnalytic.replaceDigitInsteadX(rightPartLexemes, answer);
            double leftAnswer = syntaxAnalytic.expr(lexemeBuffer);
            lexemeBuffer.setPosition(0);
            lexemeBuffer.setLexemes(rightPartLexemes);
            double rightAnswer = syntaxAnalytic.expr(lexemeBuffer);
            if (leftAnswer != rightAnswer) {
                System.out.println("Answer is incorrect." + answer);
                return;
            }
            Optional<Equation> equationInDB = equationRepository.findByValue(equation);
            Equation equationForDb = equationInDB.orElseGet(() ->
                    equationRepository.save(new Equation(equation)));
            Optional<Answer> answerFromDB = answerRepository.findByValue(answer);
            if (answerFromDB.isEmpty()) {
                Answer answerForDB = new Answer();
                answerForDB.setValue(answer);
                answerForDB.setEquations(List.of(equationForDb));
                answerRepository.save(answerForDB);
                System.out.println("Equation and answer saved to DB.");
                return;
            }
            Answer answerSavedInDB = answerFromDB.get();
            List<Equation> newEquations = answerSavedInDB.getEquations();
            newEquations.add(equationForDb);
            answerSavedInDB.setEquations(newEquations);
            answerRepository.save(answerSavedInDB);
            System.out.println("Equation and answer saved to DB.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
