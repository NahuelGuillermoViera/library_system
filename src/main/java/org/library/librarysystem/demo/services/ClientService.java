package org.library.librarysystem.services;

import org.library.librarysystem.entities.Book;
import org.library.librarysystem.entities.Client;
import org.library.librarysystem.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {
    private final IClientRepository clientRepository;
    private HashMap<String, Object> data;

    @Autowired
    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    private ResponseEntity<Object> clearBooksListAndDelete(Optional<Client> client) {
        data = new HashMap<>();
        if (client.isEmpty()) {
            data.put("error", true);
            data.put("message", "Client not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Client clientToDelete = client.get();
        Set<Book> books = clientToDelete.getBooks();
        for (Book book : books) {
            book.getClients().remove(clientToDelete);
        }

        books.clear();

        this.clientRepository.delete(clientToDelete);
        data.put("success", true);
        data.put("message", "Client deleted successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    //CREATE AND UPDATE CLIENT
    public ResponseEntity<Object> save(Client client) {
        Optional<Client> clientByEmail =  this.clientRepository.findByEmail(client.getEmail());
        Optional<Client> clientByDni = this.clientRepository.findByDni( client.getDni());
        data = new HashMap<>();
        if (client.getId() == null && (clientByDni.isPresent() || clientByEmail.isPresent())) {
            data.put("error", true);
            data.put("message", "Client already exists, please update the existing");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        this.clientRepository.save(client);
        data.put("success", true);
        data.put("client", client);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    //FIND ALL
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    //FIND BY ID
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    //DELETE BY ID
    public ResponseEntity<Object> deleteById(Long id) {
        Optional<Client> client = this.clientRepository.findById(id);
        return clearBooksListAndDelete(client);
    }

}
