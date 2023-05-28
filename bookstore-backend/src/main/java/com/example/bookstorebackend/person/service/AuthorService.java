package com.example.bookstorebackend.person.service;

import com.example.bookstorebackend.book.BookRepository;
import com.example.bookstorebackend.genre.GenreRepository;
import com.example.bookstorebackend.person.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
}
