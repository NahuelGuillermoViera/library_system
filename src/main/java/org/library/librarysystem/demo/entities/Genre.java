package org.library.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @Transient
    private int bookCount;

    @ManyToMany(mappedBy = "genres", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("genre")
    private Set<Book> books;

    public Genre() {
    }

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Genre(Set<Book> books, String description, String name) {
        this.books = books;
        this.description = description;
        this.name = name;
    }

    public Genre(Long id, String name, String description, Set<Book> books) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.books = books;
    }

    public Integer getBookCount() {
        if (books == null) {
            return 0;
        }
        bookCount = books.size();
        return bookCount;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getGenres().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getGenres().remove(this);
    }
}
