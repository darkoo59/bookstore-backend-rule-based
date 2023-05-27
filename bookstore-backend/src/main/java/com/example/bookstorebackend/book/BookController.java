package com.example.bookstorebackend.book;

import com.example.bookstorebackend.book.dto.BookDTO;
import com.example.bookstorebackend.security.filter.AuthUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.bookstorebackend.utils.ObjectsMapper.convertBooksToDTOs;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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
    public ResponseEntity<List<BookDTO>> getAll(){
        return new ResponseEntity<>(convertBooksToDTOs(bookService.getAll()), OK);
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<BookCharacteristics>> getAllWithCharacteristics() {
        return new ResponseEntity<>(bookService.getAllWithCharacteristics(), OK);
    }

    @GetMapping(path = "recommended")
    public ResponseEntity<List<BookDTO>> getRecommended(HttpServletRequest request) {
//        String email = AuthUtility.getEmailFromRequest(request);
        String email = "darkoo59@gmail.com";
        try{
            List<BookDTO> books = convertBooksToDTOs(bookService.getRecommendedBooks(email));
            return new ResponseEntity<>(books, OK);
        }catch (Exception e){
            return new ResponseEntity<>(BAD_REQUEST);
        }

    }
    @GetMapping(path="/byId/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<Book> getById(@PathVariable("id") String id){
        return new ResponseEntity<>(bookService.getById(Integer.valueOf(id)), OK);
    }

}
