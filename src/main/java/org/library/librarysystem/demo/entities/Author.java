package org.library.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(unique = true)
    private String email;

    @ManyToMany(mappedBy = "authors", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("authors")
    private Set<Book> books;

    public Author() {
    }

    public Author(String email) {
        this.email = email;
    }

    public Author(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public Author(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Author(Long id, String nickname, String email, Set<Book> books) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getAuthors().remove(this);
    }
}
