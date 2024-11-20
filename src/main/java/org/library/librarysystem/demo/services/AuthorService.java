package org.library.librarysystem.services;

import org.library.librarysystem.entities.Author;
import org.library.librarysystem.entities.Book;
import org.library.librarysystem.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {
    private final IAuthorRepository authorRepository;
    private HashMap<String, Object> data;

    @Autowired
    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private ResponseEntity<Object> clearBooksListAndDelete (Optional<Author> author) {
        data = new HashMap<>();
        if (author.isEmpty()) {
            data.put("error", true);
            data.put("message", "Author not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Set<Book> books = author.get().getBooks();
        for (Book book : books) {
            book.getAuthors().remove(author.get());
        }
        this.authorRepository.deleteById(author.get().getId());
        data.put("success", true);
        data.put("message", "Book deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> save(Author author) {
        Optional<Author> existingAuthor = this.authorRepository.findByEmail(author.getEmail());
        data = new HashMap<>();
        if (existingAuthor.isPresent() && author.getId() == null) {
            data.put("error", true);
            data.put("message", "Title already exists");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        this.authorRepository.save(author);
        data.put("success", true);
        data.put("message", "Author saved successfully");
        data.put("author", author);

        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    public ResponseEntity<Object> deleteById(Long id) {
        return this.clearBooksListAndDelete(this.authorRepository.findById(id));
    }

    public ResponseEntity<Object> deleteByEmail(String email) {
        data = new HashMap<>();
        this.authorRepository.findByEmail(email).ifPresent(author -> {
            this.clearBooksListAndDelete(this.authorRepository.findById(author.getId()));
        });
        data.put("error", true);
        data.put("message", "Author not found");
        return new ResponseEntity<>(
                data,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<Object> deleteAll() {
        data = new HashMap<>();
        List<Author> authors = this.authorRepository.findAll();
        if (authors.isEmpty()) {
            data.put("error", true);
            data.put("message", "Authors not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        for (Author author : authors) {
            this.clearBooksListAndDelete(this.authorRepository.findById(author.getId()));
        }
        this.authorRepository.deleteAll();
        data.put("success", true);
        data.put("message", "Authors deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

}
