package org.library.librarysystem.controllers;

import org.library.librarysystem.entities.Client;
import org.library.librarysystem.services.relations.BookClientService;
import org.library.librarysystem.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final BookClientService bookClientService;

    @Autowired
    public ClientController(ClientService clientService, BookClientService bookClientService) {
        this.clientService = clientService;
        this.bookClientService = bookClientService;
    }

    //FIND ALL
    @GetMapping
    public List<Client> getAllClients() {
        return this.clientService.findAll();
    }

    //FIND BY ID
    @GetMapping("/id/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return this.clientService.findById(id);
    }

    //CREATE BOOK
    @PostMapping
    public ResponseEntity<Object> addClient(@RequestBody Client client) {
        return this.clientService.save(client);
    }

    //UPDATE BOOK
    @PutMapping
    public ResponseEntity<Object> updateClient(@RequestBody Client client) {
        return this.clientService.save(client);
    }

    //DELETE BOOK BY ID
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        return this.clientService.deleteById(id);
    }
}
