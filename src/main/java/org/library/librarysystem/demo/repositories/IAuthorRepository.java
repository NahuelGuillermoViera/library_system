package org.library.librarysystem.repositories;

import org.library.librarysystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByEmail(String email);
}
