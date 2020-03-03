package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHORS_FILE_PATH = "D:\\SoftUni\\04. Java DB\\Hibernate\\06. Spring Data Advanced Querying\\Projects\\ExerciseSpringDataAdvancedQuerying\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public String findAuthorsWithNamesEndingWith(String pattern) {
        return this.authorRepository.findAllByFirstNameEndingWith(pattern)
                .stream()
                .map(a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
                .reduce((f, s) -> f + System.lineSeparator() + s).orElse("");
    }

    @Override
    public String findTotalAmountOfBooksWrittenBy(String name) {
        String firstName = name.split("\\s+")[0];
        String lastName = name.split("\\s+")[1];
        List<Object[]> authorInfo = this.authorRepository.findTotalAmountOfBooksWrittenBy(firstName, lastName);

        return String.format("%s %s has written %s books", firstName, lastName, authorInfo.get(0)[2]);
    }
}
