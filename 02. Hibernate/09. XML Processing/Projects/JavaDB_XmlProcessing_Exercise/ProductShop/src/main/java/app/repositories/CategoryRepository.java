package app.repositories;

import app.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT DISTINCT c FROM Category c INNER JOIN FETCH c.products p " +
            "ORDER BY SIZE(c.products) DESC")
    List<Category> categoriesByProductsCount();
}
