package com.example.bookstorebackend.book;

import com.example.bookstorebackend.person.service.PersonService;
import com.example.bookstorebackend.person.service.UserService;
import com.example.bookstorebackend.security.filter.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/bookstore/book")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return new ResponseEntity<>(bookService.getAll(), OK);
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<BookCharacteristics>> getAllWithCharacteristics() {
        return new ResponseEntity<>(bookService.getAllWithCharacteristics(), OK);
    }
}
