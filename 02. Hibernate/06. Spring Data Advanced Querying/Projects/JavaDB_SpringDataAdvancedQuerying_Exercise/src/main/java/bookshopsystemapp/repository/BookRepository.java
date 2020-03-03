package bookshopsystemapp.repository;

import bookshopsystemapp.domain.dto.ReducedBook;
import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lesser, BigDecimal greater);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByTitleContaining(String text);

    @Query(value = "SELECT b FROM Book b JOIN b.author a WHERE b.author.lastName LIKE :pattern")
    List<Book> findAllByAuthorLastNameStartingWith(@Param(value = "pattern") String pattern);

    @Query(value = "SELECT b FROM Book b WHERE LENGTH(b.title) > :length")
    List<Book> findAllByTitleLongerThan(@Param(value = "length") int length);

    ReducedBook findBookByTitle(String title);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Book b SET b.copies = b.copies + :amount WHERE :date < b.releaseDate")
    void increaseBookCopies(@Param(value = "date") LocalDate localDate,
                            @Param(value = "amount") int amount);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Book b WHERE b.copies < :copies")
    void removeBooksByCopies(@Param(value = "copies") int copies);

    List<Book> findAllByCopiesLessThan(int copies);

    @Query(value = "SELECT a.firstName, a.lastName, SUM(b.copies)FROM Book b JOIN b.author a GROUP BY b.author.id ORDER BY SUM(b.copies) DESC")
    List<Object[]> findCopiesByAuthor();

}
