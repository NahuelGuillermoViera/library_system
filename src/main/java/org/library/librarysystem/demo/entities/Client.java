package org.library.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
public class Client {

    @Id
    @Column(name = "id_document")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private Long dni;

    @ManyToMany(mappedBy = "clients", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("clients")
    private Set<Book> books;

    @Transient
    private int booksCount;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String email, String phone, String address, Set<Book> books, Long dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.books = books;
        this.dni = dni;
    }

    public Client(String firstName, String lastName, String email, String phone, String address, Long dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dni = dni;
    }

    public Integer getBooksCount() {
        if (books == null) {
            return 0;
        }
        booksCount = books.size();
        return booksCount;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getClients().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getClients().remove(this);
    }
}
