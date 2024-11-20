package org.library.librarysystem.services.relations;

import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Client;
import org.library.librarysystem.repositories.IBookRepository;
import org.library.librarysystem.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class BookClientService {
    private final IClientRepository clientRepository;
    private final IBookRepository bookRepository;
    private HashMap<String, Object> data;

    @Autowired
    public BookClientService(IClientRepository clientRepository, IBookRepository bookRepository, HashMap<String, Object> data) {
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
        this.data = data;
    }

    public ResponseEntity<Object> relationBookClient(Long idClient, Long idBook){
        data = new HashMap<>();
        Optional<Client> client = clientRepository.findById(idClient);
        Optional<Book> book = bookRepository.findById(idBook);

        if(client.isPresent() && book.isPresent()){
            client.get().addBook(book.get());
            this.clientRepository.save(client.get());
            this.bookRepository.save(book.get());
            data.put("message", "Libro insertado en cliente " + client.get().getFirstName());
            data.put("client", client.get());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        data.put("error", true);
        data.put("message", "Elementos no encontrados");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Object> deleteRelationBookClient(Long idClient, Long idBook){
        data = new HashMap<>();
        Optional<Client> client = clientRepository.findById(idClient);
        Optional<Book> book = bookRepository.findById(idBook);

        if(client.isPresent() && book.isPresent()){
            client.get().removeBook(book.get());
            this.clientRepository.save(client.get());
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
