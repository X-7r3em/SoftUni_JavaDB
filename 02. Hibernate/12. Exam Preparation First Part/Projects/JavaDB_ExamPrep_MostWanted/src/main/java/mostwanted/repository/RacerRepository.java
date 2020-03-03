package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {

    Racer findByName(String name);

    @Query(value = "SELECT r FROM Racer r WHERE SIZE(r.cars) > 0 ORDER BY SIZE(r.cars) DESC, r.name ASC")
    List<Racer> findAllRacers();

}
