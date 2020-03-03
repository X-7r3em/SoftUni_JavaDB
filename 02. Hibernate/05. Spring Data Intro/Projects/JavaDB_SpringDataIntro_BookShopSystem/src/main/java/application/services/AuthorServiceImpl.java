package application.services;

import application.entities.Author;
import application.repositories.AuthorRepository;
import application.services.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void saveAuthor(Author author) {
        this.authorRepository.save(author);
    }

    @Override
    public boolean isEmpty(){
        return this.authorRepository.count() == 0;
    }

    @Override
    public List<Author> findAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public List<Author> findAuthorsByBooksCount() {
        return this.authorRepository.findAll().stream()
                .sorted((f,s) -> Integer.compare(s.getBooks().size(), f.getBooks().size()))
                .collect(Collectors.toList());
    }
}
