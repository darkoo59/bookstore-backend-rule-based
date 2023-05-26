package com.example.bookstorebackend.person.repository;

import com.example.bookstorebackend.person.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository  extends JpaRepository<Author, Long> {

}
