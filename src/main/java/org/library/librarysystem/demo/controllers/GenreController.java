package org.library.librarysystem.controllers;

import org.library.librarysystem.entities.Genre;
import org.library.librarysystem.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> findAll() {
        return this.genreService.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Genre> findById(@PathVariable Long id) {
        return this.genreService.findById(id);
    }


    @GetMapping("/name/{name}")
    public Optional<Genre> findByName(@PathVariable String name) {
        return this.genreService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Genre genre) {
        return this.genreService.save(genre);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Genre genre) {
        return this.genreService.save(genre);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.genreService.delete(id);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return this.genreService.deleteAll();
    }


}
