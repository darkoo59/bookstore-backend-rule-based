package com.example.bookstorebackend.book;

import com.example.bookstorebackend.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

}
