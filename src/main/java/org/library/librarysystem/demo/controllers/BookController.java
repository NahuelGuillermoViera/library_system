package org.library.librarysystem.controllers;


import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Client;
import org.library.librarysystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //FIND ALL
    @GetMapping
    public List<Book> getAllBooks() {
        return  this.bookService.findAll();
    }

    //FIND BY ID
    @GetMapping("/id/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return this.bookService.findById(id);
    }

    //CREATE BOOK
    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        return this.bookService.save(book);
    }

    //UPDATE BOOK
    @PutMapping
    public ResponseEntity<Object> updateBook(@RequestBody Book book) {
        return this.bookService.save(book);
    }

    //DELETE BOOK BY ID
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        return this.bookService.deleteById(id);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<Object> deleteBookByTitle(@PathVariable String title) {
        Optional<Book> book = this.bookService.findByTitle(title);
        if (book.isPresent()) {
            Set<Client> clients = book.get().getClients();
            if (clients == null || clients.isEmpty()) {
                return this.bookService.deleteByTitle(title);
            }
            return this.bookService.deleteByTitle(title);
        }
        return ResponseEntity.noContent().build();
    }

}
