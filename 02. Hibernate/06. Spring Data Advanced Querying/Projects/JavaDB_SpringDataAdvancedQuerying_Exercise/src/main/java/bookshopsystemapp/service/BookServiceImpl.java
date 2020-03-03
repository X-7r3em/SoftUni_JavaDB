package bookshopsystemapp.service;

import bookshopsystemapp.domain.dto.ReducedBook;
import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "D:\\SoftUni\\04. Java DB\\Hibernate\\06. Spring Data Advanced Querying\\Projects\\ExerciseSpringDataAdvancedQuerying\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(b -> b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    @Override
    public String getAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        List<String> books = this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        return String.join(System.lineSeparator(), books);
    }

    @Override
    public String getAllBooksByEditionTypeAndCopiesUnder() {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream()
                .map(Book::getTitle)
                .reduce((a, b) -> a + System.lineSeparator() + b).orElse("");
    }

    @Override
    public String getAllBooksWithPriceLesserOrGreaterThan() {
        return this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(new BigDecimal("5"), new BigDecimal("40"))
                .stream()
                .map(b -> String.format("%s - $%s", b.getTitle(), b.getPrice()))
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public String getAllBooksBeforeAndAfterAYear(String year) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate before = LocalDate.parse(year + "-01-01", dtf);
        LocalDate after = LocalDate.parse(year + "-12-31", dtf);
        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after)
                .stream()
                .map(Book::getTitle)
                .reduce("", (f, s) -> f + System.lineSeparator() + s);
    }

    @Override
    public String getAllBooksReleasedBefore(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dtf);
        return this.bookRepository.findAllByReleaseDateBefore(localDate)
                .stream()
                .map(b -> String.format("%s %s %s",
                        b.getTitle(), b.getEditionType().toString(), b.getPrice().toString()))
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public String getAllBooksTitlesContaining(String text) {
        return this.bookRepository.findAllByTitleContaining(text)
                .stream()
                .map(Book::getTitle)
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public String getAllBooksWithAuthorLastNameStartingWith(String pattern) {
        pattern = pattern + "%";
        return this.bookRepository.findAllByAuthorLastNameStartingWith(pattern)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public int getAllBooksWithTitleLongerThan(int length) {
        return this.bookRepository.findAllByTitleLongerThan(length).size();
    }

    @Override
    public String findCopiesByAuthor() {
        return this.bookRepository.findCopiesByAuthor().stream()
                .map(o -> String.format("%s %s - %s", o[0], o[1], o[2]))
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public String getBookReducedInformation(String title) {
        ReducedBook rb = this.bookRepository.findBookByTitle(title);
        return String.format("%s %s %s %s",
                rb.getTitle(), rb.getEditionType(), rb.getAgeRestriction(), rb.getPrice());
    }

    @Override
    public int increaseBookCopies(String date, int amount) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        this.bookRepository.increaseBookCopies(localDate, amount);

        return this.bookRepository.findAllByReleaseDateAfter(localDate).size() * amount;
    }

    @Override
    public int deleteBooksByCopies(int copies) {
        int deletedBooks = this.bookRepository.findAllByCopiesLessThan(copies).size();

        this.bookRepository.removeBooksByCopies(copies);

        return deletedBooks;
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
