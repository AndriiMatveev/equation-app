package equation.app.repository;

import equation.app.model.Answer;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("from Answer a left join fetch a.equations where a.value = ?1")
    Optional<Answer> findByValue(BigDecimal value);
}
