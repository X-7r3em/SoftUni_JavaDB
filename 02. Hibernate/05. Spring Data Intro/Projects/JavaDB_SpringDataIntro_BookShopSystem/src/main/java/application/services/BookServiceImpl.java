package application.services;

import application.entities.Book;
import application.repositories.BookRepository;
import application.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public void save(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public boolean isEmpty() {
        return this.bookRepository.count() == 0;
    }

    @Override
    public List<Book> findBooksAfterYear() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return this.bookRepository.
                findBooksByReleaseDateGreaterThan(sdf.parse("2000-01-01"));
    }

    @Override
    public List<Book> findBooksBeforeYear() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return this.bookRepository.
                findBooksByReleaseDateGreaterThan(sdf.parse("1990-01-01"));
    }

    @Override
    public List<Book> findBooksFromAuthor() {
        return this.bookRepository.findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc("George", "Powell");
    }
}
