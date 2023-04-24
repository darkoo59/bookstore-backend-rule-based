package com.example.bookstorebackend.person.repository;

import com.example.bookstorebackend.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
