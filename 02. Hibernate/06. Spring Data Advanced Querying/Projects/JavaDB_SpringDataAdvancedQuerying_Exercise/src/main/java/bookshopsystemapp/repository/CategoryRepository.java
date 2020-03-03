package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM categories AS c WHERE c.name = :name", nativeQuery = true)
    List<Object[]> selectCategories(@Param(value = "name") String name);
}
