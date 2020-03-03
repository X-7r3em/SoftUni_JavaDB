package application.repositories;

import application.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByReleaseDateGreaterThan(Date year);
    List<Book> findBooksByReleaseDateLessThan(Date year);
    List<Book> findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc(String firstName, String lastName);
}
