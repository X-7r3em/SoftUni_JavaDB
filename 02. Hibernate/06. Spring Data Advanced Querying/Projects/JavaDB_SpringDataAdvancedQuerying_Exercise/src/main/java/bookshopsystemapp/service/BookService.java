package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.AgeRestriction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    String getAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    String getAllBooksByEditionTypeAndCopiesUnder();

    String getAllBooksWithPriceLesserOrGreaterThan();

    String getAllBooksBeforeAndAfterAYear(String year);

    String getAllBooksReleasedBefore(String date);

    String getAllBooksTitlesContaining(String text);

    String getAllBooksWithAuthorLastNameStartingWith(String pattern);

    int getAllBooksWithTitleLongerThan(int length);

    String getBookReducedInformation(String title);

    int increaseBookCopies(String date, int amount);

    int deleteBooksByCopies(int copies);

    String findCopiesByAuthor();
}
