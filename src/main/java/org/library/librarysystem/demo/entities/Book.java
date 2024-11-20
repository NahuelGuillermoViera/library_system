package org.library.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "rel_books_genres",
            joinColumns = @JoinColumn(name = "FK_BOOK", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_GENRE", nullable = false)
    )
    @JsonIgnoreProperties("books")
    private Set<Genre> genres;



    @ManyToMany
    @JoinTable(
            name = "rel_books_clients",
            joinColumns = @JoinColumn(name = "FK_BOOK", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_CLIENT", nullable = false)
    )
    @JsonIgnoreProperties("books")
    private Set<Client> clients;

    @ManyToMany
    @JoinTable(
            name = "rel_books_authors",
            joinColumns = @JoinColumn(name = "FK_BOOK", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_AUTHOR", nullable = false)
    )
    @JsonIgnoreProperties("books")
    private Set<Author> authors;

    public Book(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

    public Book(String title, LocalDate date, Set<Genre> genres) {
        this.title = title;
        this.date = date;
        this.genres = genres;
    }

    public Book(String title, LocalDate date, Set<Genre> genres, Set<Client> clients) {
        this.title = title;
        this.date = date;
        this.genres = genres;
        this.clients = clients;
    }

    public Book(String title, LocalDate date, Set<Genre> genres, Set<Client> clients, Set<Author> authors) {
        this.title = title;
        this.date = date;
        this.genres = genres;
        this.clients = clients;
        this.authors = authors;
    }

    public Book(Long id, String title, LocalDate date, Set<Genre> genres, Set<Client> clients, Set<Author> authors) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.genres = genres;
        this.clients = clients;
        this.authors = authors;
    }

    public Book() {
    }

    public void addClient(Client client) {
        this.clients.add(client);
        client.getBooks().add(this);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
        client.getBooks().remove(this);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().remove(this);
    }

    public void addAuthor(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeAuthor(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().remove(this);
    }
}
