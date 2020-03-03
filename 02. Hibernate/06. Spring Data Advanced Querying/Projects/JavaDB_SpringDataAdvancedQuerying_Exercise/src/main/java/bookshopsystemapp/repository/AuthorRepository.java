package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAllByFirstNameEndingWith(String pattern);

    @Query(value = "CALL udp_select_authors(:first_name, :last_name)", nativeQuery = true)
    List<Object[]> findTotalAmountOfBooksWrittenBy(@Param("first_name") String firstName, @Param("last_name") String lastName);
}
