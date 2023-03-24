package equation.app.repository;

import equation.app.model.Equation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Long> {
    @Query("from Equation e where e.value = ?1")
    Optional<Equation> findByValue(String value);
}
