package org.library.librarysystem.controllers;

import org.library.librarysystem.services.relations.BookAuthorService;
import org.library.librarysystem.services.relations.BookClientService;
import org.library.librarysystem.services.relations.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RequestController {

    private final BookClientService bookClientService;
    private final BookAuthorService bookAuthorService;
    private final BookGenreService bookGenreService;

    @Autowired
    public RequestController(BookClientService bookClientService, BookAuthorService bookAuthorService, BookGenreService bookGenreService) {
        this.bookClientService = bookClientService;
        this.bookAuthorService = bookAuthorService;
        this.bookGenreService = bookGenreService;
    }

    //BOOK CLIENT REALTION
    @PutMapping("/client/{idClient}/book/{idBook}")
    public ResponseEntity<Object> relationBookClient(@PathVariable Long idClient, @PathVariable Long idBook) {
        return this.bookClientService.relationBookClient(idClient, idBook);
    }

    @DeleteMapping("/client/{idClient}/book/{idBook}")
    public ResponseEntity<Object> deleteRelationBookClient(@PathVariable Long idClient, @PathVariable Long idBook) {
        return this.bookClientService.deleteRelationBookClient(idClient, idBook);
    }

    //BOOK AUTHOR RELATION
    @PutMapping("/author/{idAuthor}/book/{idBook}")
    public ResponseEntity<Object> relationBookAuthor(@PathVariable Long idAuthor, @PathVariable Long idBook) {
        return this.bookAuthorService.relationBookAuthor(idAuthor, idBook);
    }

    @DeleteMapping("/author/{idAuthor}/book/{idBook}")
    public ResponseEntity<Object> deleteRelationBookAuthor(@PathVariable Long idAuthor, @PathVariable Long idBook) {
        return this.bookAuthorService.deleteRelationBookAuthor(idAuthor, idBook);
    }

    //BOOK GENRE RELATION
    @PutMapping("/genre/{idGenre}/book/{idBook}")
    public ResponseEntity<Object> relationBookGenre(@PathVariable Long idGenre, @PathVariable Long idBook) {
        return this.bookGenreService.relationBookClient(idGenre, idBook);
    }

    @DeleteMapping("/genre/{idGenre}/book/{idBook}")
    public ResponseEntity<Object> deleteRelationBookGenre(@PathVariable Long idGenre, @PathVariable Long idBook) {
        return this.bookGenreService.deleteRelationBookClient(idGenre, idBook);
    }

}
