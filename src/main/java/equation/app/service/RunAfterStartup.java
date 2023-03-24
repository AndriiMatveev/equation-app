package equation.app.service;

import java.util.Scanner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartup {
    private final Menu menu;

    public RunAfterStartup(Menu menu) {
        this.menu = menu;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        System.out.println("Hello. This is simple equation calculator."
                + " Please, select an action below:"
                + System.lineSeparator());
        Scanner sc = new Scanner(System.in);
        while (true) {
            menu.chooseAction();
        }
    }
}
