package com.example.bookstorebackend.book;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
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
}
