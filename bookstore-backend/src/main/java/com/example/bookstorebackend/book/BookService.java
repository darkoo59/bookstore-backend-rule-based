package com.example.bookstorebackend.book;

import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.person.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final KieContainer kieContainer;

    public List<Book> getAll() { return this.bookRepository.findAll(); }
    public Book getById(long id) { return this.bookRepository.findById(id).get();}

    public double validateBookPrice(Book book) {
        KieSession kiesession = kieContainer.newKieSession();
        kiesession.insert(book);
        kiesession.fireAllRules();
        kiesession.dispose();
        return book.getPrice();
    }

    public List<Book> getRecommendedBooks(){
        PriorityQueue<Author> popularAuthors = new PriorityQueue<>(10,
                Comparator.comparingInt(Author::getTotalGrades));
        List<Book> recommendedBooks = new ArrayList<>();

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("popularAuthors", popularAuthors);
        kieSession.setGlobal("recommendedBooks", recommendedBooks);
        List<Author> authors = authorRepository.findAll();
        for (Author author : authors) {
            kieSession.insert(author);
        }
        List<Book> books = bookRepository.findAll();
        for(Book book : books){
            kieSession.insert(book);
        }
        kieSession.fireAllRules();
        for (Book book : recommendedBooks)
            System.out.println(book.getTitle() + ": " + book.getTotalGrades());
        for (Author author : popularAuthors)
            System.out.println(author.getName() + ": " + author.getTotalGrades());

        kieSession.dispose();
        return recommendedBooks;
    }
}
