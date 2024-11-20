package org.library.librarysystem.services;

import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Genre;
import org.library.librarysystem.repositories.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GenreService {
    private final IGenreRepository genreRepository;
    private HashMap<String, Object> data;

    @Autowired
    public GenreService(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    private ResponseEntity<Object> clearBooksListAndDelete (Optional<Genre> genre) {
        data = new HashMap<>();
        if (genre.isEmpty()) {
            data.put("error", true);
            data.put("message", "Genre not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Set<Book> books = genre.get().getBooks();
        for (Book book : books) {
            book.getGenres().remove(genre.get());
        }
        this.genreRepository.deleteById(genre.get().getId());
        data.put("success", true);
        data.put("message", "Book deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }



    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }

    public Optional<Genre> findById(Long id) {
        return this.genreRepository.findById(id);
    }

    public Optional<Genre> findByName(String name) {
        return this.genreRepository.findByName(name);
    }

    public ResponseEntity<Object> save(Genre genre) {
        Optional<Genre> existingGenre = this.genreRepository.findByName(genre.getName());
        data = new HashMap<>();
        if (existingGenre.isPresent() && genre.getId() == null) {
            data.put("error", true);
            data.put("message", "Name already exists");

            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        this.genreRepository.save(genre);
        data.put("success", true);
        data.put("message", "Author saved successfully");
        data.put("genre", genre);

        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> delete(Long id) {
        Optional<Genre> existingGenre = this.genreRepository.findById(id);
        return this.clearBooksListAndDelete(existingGenre);
    }

    public ResponseEntity<Object> deleteAll() {
        data = new HashMap<>();
        List<Genre> genres = this.genreRepository.findAll();
        if (genres.isEmpty()) {
            data.put("error", true);
            data.put("message", "Genre not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        for (Genre genre : genres) {
            this.clearBooksListAndDelete(this.genreRepository.findById(genre.getId()));
        }
        this.genreRepository.deleteAll();
        data.put("success", true);
        data.put("message", "Authors deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
