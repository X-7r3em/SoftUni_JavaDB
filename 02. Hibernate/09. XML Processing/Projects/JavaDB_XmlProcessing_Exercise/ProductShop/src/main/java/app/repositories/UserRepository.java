package app.repositories;

import app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT DISTINCT u FROM User u INNER JOIN FETCH u.soldProducts p WHERE p.buyer IS NOT NULL " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllWithProductsSold();

    @Query(value = "SELECT DISTINCT u FROM User u INNER JOIN FETCH u.soldProducts p WHERE p.buyer IS NOT NULL " +
            "ORDER BY SIZE(u.soldProducts) DESC, u.lastName ASC")
    List<User> usersAndProducts();
}
