package bookshopsystemapp.service;

import java.io.IOException;

public interface AuthorService {

    void seedAuthors() throws IOException;

    String findAuthorsWithNamesEndingWith(String pattern);

    String findTotalAmountOfBooksWrittenBy(String name);

}
