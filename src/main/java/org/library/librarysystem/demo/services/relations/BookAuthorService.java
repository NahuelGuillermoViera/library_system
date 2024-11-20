package org.library.librarysystem.services.relations;

import org.library.librarysystem.entities.Author;
import org.library.librarysystem.entities.Book;
import org.library.librarysystem.repositories.IAuthorRepository;
import org.library.librarysystem.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class BookAuthorService {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private HashMap<String, Object> data;

    @Autowired
    public BookAuthorService(IBookRepository bookRepository, IAuthorRepository authorRepository, HashMap<String, Object> data) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.data = data;
    }

    public ResponseEntity<Object> relationBookAuthor(Long idAuthor, Long idBook){
        Optional<Author> author = this.authorRepository.findById(idAuthor);
        Optional<Book> book = bookRepository.findById(idBook);
        data = new HashMap<>();

        if(author.isPresent() && book.isPresent()){
            author.get().addBook(book.get());
            this.authorRepository.save(author.get());
            this.bookRepository.save(book.get());
            data.put("message", "Libro insertado en autor " + author.get().getNickname());
            data.put("client", author.get());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        data.put("error", true);
        data.put("message", "Elementos no encontrados");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Object> deleteRelationBookAuthor(Long idAuthor, Long idBook){
        Optional<Author> author = authorRepository.findById(idAuthor);
        Optional<Book> book = bookRepository.findById(idBook);
        data = new HashMap<>();

        if(author.isPresent() && book.isPresent()){
            author.get().removeBook(book.get());
            this.authorRepository.save(author.get());
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
