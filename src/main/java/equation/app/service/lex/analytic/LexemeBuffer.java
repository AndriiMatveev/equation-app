package equation.app.service.lex.analytic;

import equation.app.model.lexeme.Lexeme;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class LexemeBuffer {
    private int position;
    private List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(position++);
    }

    public void back() {
        position--;
    }
}
