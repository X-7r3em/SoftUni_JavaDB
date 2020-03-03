package bookshopsystemapp.controller;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final BufferedReader br;

    public BookshopController(
            AuthorService authorService, CategoryService categoryService,
            BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();

//        this.booksTitlesByAgeRestriction();
//        this.goldenBooks();
//        this.booksByPrice();
//        this.notReleasedBooks();
//        this.booksReleasedBeforeDate();
//        this.authorsSearch();
//        this.booksSearch();
//        this.bookTitlesSearch();
//        this.countBooks();
//        this.totalBookCopies();
//        this.reducedBook();
//        this.increaseBookCopies();
//        this.removeBooks();
//        this.storedProcedure();
    }

    //01. Books Titles by Age Restriction
    private void booksTitlesByAgeRestriction() throws IOException {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(this.br.readLine().toUpperCase());
        System.out.println(this.bookService.getAllBooksByAgeRestriction(ageRestriction));
    }

    //2. Golden Books
    private void goldenBooks() throws IOException {
        System.out.println(this.bookService.getAllBooksByEditionTypeAndCopiesUnder());
    }

    //3. Books by Price
    private void booksByPrice() {
        System.out.println(this.bookService.getAllBooksWithPriceLesserOrGreaterThan());
    }

    //4. Not Released Books
    private void notReleasedBooks() throws IOException {
        System.out.println(this.bookService.getAllBooksBeforeAndAfterAYear(this.br.readLine()));
    }

    //5. Books Released Before Date
    private void booksReleasedBeforeDate() throws IOException {
        System.out.println(this.bookService.getAllBooksReleasedBefore(this.br.readLine()));
    }

    //6. Authors Search
    private void authorsSearch() throws IOException {
        System.out.println(this.authorService.findAuthorsWithNamesEndingWith(this.br.readLine()));
    }

    //7. Books Search
    private void booksSearch() throws IOException {
        System.out.println(this.bookService.getAllBooksTitlesContaining(this.br.readLine()));
    }

    //8. Book Titles Search
    private void bookTitlesSearch() throws IOException {
        System.out.println(this.bookService.getAllBooksWithAuthorLastNameStartingWith(this.br.readLine()));
    }

    //9. Count Books
    private void countBooks() throws IOException {
        int length = Integer.parseInt(this.br.readLine());
        System.out.println(this.bookService.getAllBooksWithTitleLongerThan(length));
    }

    //10. Total Book Copies
    private void totalBookCopies() {
        System.out.println(this.bookService.findCopiesByAuthor());
    }

    //11. Reduced Book
    private void reducedBook() throws IOException {
        //Ако не приеме входа за Thrones го напиши ръчно и си върви задачата. Иначе може да вхръли NullPoint. Може би в word има някакъв формат, където завлича.
        System.out.println(this.bookService.getBookReducedInformation(this.br.readLine()));
    }

    //12. Increase Book Copies
    private void increaseBookCopies() throws IOException {
        String date = this.br.readLine();
        int amount = Integer.parseInt(this.br.readLine());
        System.out.println(this.bookService.increaseBookCopies(date, amount));
    }

    //13. Remove Books
    private void removeBooks() throws IOException {
        int copies = Integer.parseInt(this.br.readLine());
        System.out.println(this.bookService.deleteBooksByCopies(copies));
    }

    //14. Stored Procedure
    private void storedProcedure() throws IOException {
        /*
        DELIMITER $$
        CREATE PROCEDURE udp_select_authors(firstName VARCHAR(50), lastName VARCHAR(50))
        BEGIN
        SELECT a.first_name, a.last_name, COUNT(*) count
        FROM authors a
        JOIN books b on a.id = b.author_id
        WHERE a.first_name = firstName AND a.last_name = lastName;
        END $$
         */

        System.out.println(this.authorService.findTotalAmountOfBooksWrittenBy(this.br.readLine()));
    }

}
