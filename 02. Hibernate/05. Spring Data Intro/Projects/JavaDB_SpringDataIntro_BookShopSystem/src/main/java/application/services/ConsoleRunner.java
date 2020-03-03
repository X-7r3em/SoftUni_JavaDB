package application.services;

import application.entities.Author;
import application.entities.Book;
import application.entities.Category;
import application.enumerations.AgeRestriction;
import application.enumerations.EditionType;
import application.services.interfaces.AuthorService;
import application.services.interfaces.BookService;
import application.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final String FILE_PATH = "D:\\SoftUni\\04. Java DB\\02. Hibernate\\05. Spring Data Intro\\Projects\\BookShopSystem\\src\\main\\resources\\";

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;


    @Override
    public void run(String... args) throws Exception {
        this.seedDatabase();
        this.printTitlesAfter2000();
        this.printAuthorsWithBookBefore();
        this.printAllAuthorsByBookCount();
        this.findAuthorsBooks();
    }

    private void seedDatabase() throws IOException, ParseException {
        this.seedAuthors();
        this.seedCategories();
        this.seedBooks();
    }

    private void seedAuthors() throws IOException {
        if (!this.authorService.isEmpty()) {
            return;
        }

        BufferedReader authorReader = new BufferedReader(new FileReader(FILE_PATH + "authors.txt"));
        String line;
        while ((line = authorReader.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            Author author = new Author(tokens[0], tokens[1]);
            this.authorService.saveAuthor(author);
        }
    }

    private void seedCategories() throws IOException {
        if (!this.categoryService.isEmpty()) {
            return;
        }

        BufferedReader authorReader = new BufferedReader(new FileReader(FILE_PATH + "categories.txt"));
        String line;
        while ((line = authorReader.readLine()) != null) {
            if (line.length() == 0) {
                continue;
            }
            Category category = new Category(line);
            this.categoryService.saveCategory(category);
        }
    }

    private void seedBooks() throws IOException, ParseException {
        if (!this.bookService.isEmpty()) {
            return;
        }

        String line;
        BufferedReader booksReader = new BufferedReader(new FileReader(FILE_PATH + "books.txt"));
        List<Author> authors = this.authorService.findAllAuthors();
        while ((line = booksReader.readLine()) != null) {
            String[] data = line.split("\\s+");
            Random random = new Random();
            int authorIndex = random.nextInt(authors.size());

            Author author = authors.get(authorIndex);
            EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
            Date releaseDate = formatter.parse(data[1]);
            int copies = Integer.parseInt(data[2]);
            BigDecimal price = new BigDecimal(data[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }
            titleBuilder.delete(titleBuilder.lastIndexOf(" "), titleBuilder.lastIndexOf(" "));
            String title = titleBuilder.toString();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(this.getCategories());

            this.bookService.save(book);
        }
    }

    private Set<Category> getCategories() {
        List<Category> categories = this.categoryService.getCategories();
        Random random = new Random();
        int index = random.nextInt(categories.size());

        Set<Category> bookCategories = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            bookCategories.add(categories.get(index));
        }

        return bookCategories;
    }

    private void printTitlesAfter2000() throws ParseException {
        this.bookService.findBooksAfterYear()
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void printAuthorsWithBookBefore() throws ParseException {
        this.bookService.findBooksBeforeYear()
                .forEach(b -> System.out.printf("%s %s%n", b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    private void printAllAuthorsByBookCount() {
        this.authorService.findAuthorsByBooksCount()
                .forEach(a -> System.out.printf("%s %s %s%n", a.getFirstName(), a.getLastName(), a.getBooks().size()));
    }

    private void findAuthorsBooks() {
        this.bookService.findBooksFromAuthor()
                .forEach(b -> System.out.printf("%s %s %d%n", b.getTitle(), b.getReleaseDate(), b.getCopies()));
    }
}

