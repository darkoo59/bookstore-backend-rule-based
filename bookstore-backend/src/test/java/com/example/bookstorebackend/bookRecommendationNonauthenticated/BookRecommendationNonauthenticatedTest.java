package com.example.bookstorebackend.bookRecommendationNonauthenticated;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.BookCharacteristics;
import com.example.bookstorebackend.book.BookService;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRecommendationNonauthenticatedTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private KieContainer kieContainer;

    @Test
    public void whenBookIsAddedInLastMonth_BookIsNew() {
        KieSession kiesession = kieContainer.newKieSession();
        Book book = new Book();
        book.setDateOfAddingToBookstore(LocalDate.now().minusDays(15));
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(book);
        kiesession.insert(bc);
        kiesession.fireAllRules();
        kiesession.dispose();
        assertTrue(bc.isNew());
    }

    @Test
    public void whenBookIsPublishedInLastSixMonths_BookIsNew() {
        KieSession kiesession = kieContainer.newKieSession();
        Book book = new Book();
        book.setPublishDate(LocalDate.now().minusMonths(4));
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(book);
        kiesession.insert(bc);
        kiesession.fireAllRules();
        kiesession.dispose();
        assertTrue(bc.isNew());
    }

    @Test
    public void whenBookIsNew_SuggestIt() {
        KieSession kieSession = kieContainer.newKieSession();
        Book b = new Book();
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        bc.setNew(true);
        kieSession.insert(bc);
        //kieSession.getAgenda().getAgendaGroup("suggestions").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        assertTrue(bc.isSuggested());
    }
}
