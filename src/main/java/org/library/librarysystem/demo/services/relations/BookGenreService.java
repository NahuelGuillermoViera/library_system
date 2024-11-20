package org.library.librarysystem.services.relations;

import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Genre;
import org.library.librarysystem.repositories.IBookRepository;
import org.library.librarysystem.repositories.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class BookGenreService {
    private final IBookRepository bookRepository;
    private final IGenreRepository genreRepository;
    private HashMap<String, Object> data;

    @Autowired
    public BookGenreService(IBookRepository bookRepository, IGenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<Object> relationBookClient(Long idGenre, Long idBook){
        data = new HashMap<>();
        Optional<Genre> genre = genreRepository.findById(idGenre);
        Optional<Book> book = bookRepository.findById(idBook);

        if(genre.isPresent() && book.isPresent()){
            genre.get().addBook(book.get());
            this.genreRepository.save(genre.get());
            this.bookRepository.save(book.get());
            data.put("message", "Libro insertado en genero " + genre.get().getName());
            data.put("genre", genre.get());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        data.put("error", true);
        data.put("message", "Elementos no encontrados");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Object> deleteRelationBookClient(Long idGenre, Long idBook){
        data = new HashMap<>();
        Optional<Genre> genre = genreRepository.findById(idGenre);
        Optional<Book> book = bookRepository.findById(idBook);

        if(genre.isPresent() && book.isPresent()){
            genre.get().removeBook(book.get());
            this.genreRepository.save(genre.get());
            this.bookRepository.save(book.get());
            data.put("deleted", true);
            return new ResponseEntity<>(
                    data,
                    HttpStatus.OK
            );
        }

        data.put("error", true);
        data.put("message", "Elementos no encontrados");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }


}
