package equation.app.service;

import java.util.Objects;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Menu {
    private final EquationService equationService;
    private final AnswerService answerService;

    public Menu(EquationService equationService, AnswerService answerService) {
        this.equationService = equationService;
        this.answerService = answerService;
    }

    public void chooseAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("1 - put an equation and result" + System.lineSeparator());
        System.out.println("2 - find all equations in DB by result" + System.lineSeparator());
        System.out.println("q - quit" + System.lineSeparator());

        String action = in.nextLine();
        if (action == null && (action.length() > 1 || Objects.equals(action, "1")
                || Objects.equals(action, "2")
                || Objects.equals(action, "q"))) {
            System.out.println("Please, put correct action.");
            System.out.println(System.lineSeparator());
            System.out.println("----------------------------");
            System.out.println(System.lineSeparator());
            return;
        }
        if (action.equals("q")) {
            System.exit(0);
        }
        if (action.equals("1")) {
            equationService.checkAndSaveEquation();
            System.out.println(System.lineSeparator());
            System.out.println("----------------------------");
            System.out.println(System.lineSeparator());
        }
        if (action.equals("2")) {
            answerService.printAllEquationsByAnswer();
            System.out.println(System.lineSeparator());
            System.out.println("----------------------------");
            System.out.println(System.lineSeparator());
        }
    }
}
