package equation.app.repository;

import equation.app.model.Equation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Long> {
    @Query("from Equation e where e.value = ?1")
    Optional<Equation> findByValue(String value);

    @Query (value = "SELECT * , count(answer_id) "
            + "FROM equations "
            + "JOIN equations_answers ON equations.id = equations_answers.equation_id "
            + "GROUP BY equation_id "
            + "HAVING count(answer_id ) = 1",
            nativeQuery = true)
    List<Equation> findEquationWithOneAnswer();
}
