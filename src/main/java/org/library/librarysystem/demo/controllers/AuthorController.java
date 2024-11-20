package org.library.librarysystem.controllers;

import org.library.librarysystem.entities.Author;
import org.library.librarysystem.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        return this.authorService.save(author);
    }

    @PutMapping
    public ResponseEntity<Object> updateAuthor(@RequestBody Author author) {
        return this.authorService.save(author);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Long id) {
        return this.authorService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthorById(@PathVariable Long id) {
        return this.authorService.deleteById(id);
    }

    @DeleteMapping("/email/{mail}")
    public ResponseEntity<Object> deleteAuthorByEmail(@PathVariable String mail) {
        return this.authorService.deleteByEmail(mail);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllAuthors() {
        return this.authorService.deleteAll();
    }

}
