package org.library.librarysystem.repositories;

import org.library.librarysystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
