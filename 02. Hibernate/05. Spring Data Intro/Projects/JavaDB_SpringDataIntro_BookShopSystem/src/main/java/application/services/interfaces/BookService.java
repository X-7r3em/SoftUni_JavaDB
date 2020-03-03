package application.services.interfaces;

import application.entities.Book;

import java.text.ParseException;
import java.util.List;

public interface BookService {
    void save(Book book);
    boolean isEmpty();
    List<Book> findBooksAfterYear() throws ParseException;
    List<Book> findBooksBeforeYear() throws ParseException;
    List<Book> findBooksFromAuthor();
}
