package com.example.bookstorebackend.user.repository;

import com.example.bookstorebackend.user.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
