package application.services.interfaces;

import application.entities.Author;

import java.util.List;

public interface AuthorService {
    void saveAuthor(Author author);
    boolean isEmpty();
    List<Author> findAllAuthors();
    List<Author> findAuthorsByBooksCount();
}
