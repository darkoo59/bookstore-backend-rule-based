package com.example.bookstorebackend.bookRecommendationNonauthenticated;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRecommendationNonauthenticatedTest {
    @Autowired
    private BookService bookService;

    @Test
    public void whenNegativeBookPrice_ThenBookPriceChangeToPositive() {
        Book book = new Book();
        book.setPrice(-20.2);
        bookService.validateBookPrice(book);
        double expectedPrice = 1;

        assertEquals(expectedPrice, book.getPrice());
    }
}
