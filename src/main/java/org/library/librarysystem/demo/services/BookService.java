package org.library.librarysystem.services;

import org.library.librarysystem.entities.Author;
import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Client;
import org.library.librarysystem.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    private final IBookRepository bookRepository;
    private HashMap<String, Object> data;

    @Autowired
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private ResponseEntity<Object> clearRelationsAndDelete(Optional<Book> book) {
        data = new HashMap<>();
        if (book.isEmpty()) {
            data.put("error", true);
            data.put("message", "Book not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Set<Client> clients = book.get().getClients();
        Set<Author> authors = book.get().getAuthors();
        for (Client client : clients) {
            client.getBooks().remove(book.get());
        }
        for (Author author : authors) {
            author.getBooks().remove(book.get());
        }
        this.bookRepository.deleteById(book.get().getId());
        data.put("success", true);
        data.put("message", "Book deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    //CREATE AND UPDATE BOOK
    public ResponseEntity<Object> save(Book book) {
        Optional<Book> existingBook = this.bookRepository.findByTitle(book.getTitle());
        data = new HashMap<>();
        if (existingBook.isPresent() && book.getId() == null) {
            data.put("error", true);
            data.put("message", "Title already exists");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        this.bookRepository.save(book);
        data.put("success", true);
        data.put("message", "Book saved successfully");
        data.put("book", book);

        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    //FIND ALL
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    //FIND BY ID
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    //FIND BY TITLE
    public Optional<Book> findByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    //DELETE BY ID
    public ResponseEntity<Object> deleteById(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return clearRelationsAndDelete(book);
    }

    //DELETE BY TITLE
    public ResponseEntity<Object> deleteByTitle(String title) {
        Optional<Book> book = this.bookRepository.findByTitle(title);
        return clearRelationsAndDelete(book);
    }
}
