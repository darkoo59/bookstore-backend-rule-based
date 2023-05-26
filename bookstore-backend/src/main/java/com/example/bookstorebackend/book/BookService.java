package com.example.bookstorebackend.book;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final KieContainer kieContainer;

    public List<Book> getAll() { return this.bookRepository.findAll(); }
    public Book getById(long id) { return this.bookRepository.findById(id).get();}

    public List<BookCharacteristics> getAllWithCharacteristics() {
        KieSession kieSession = kieContainer.newKieSession();
        List<Book> allBooks = this.bookRepository.findAll();
        ArrayList<BookCharacteristics> bookCharacteristicsList = new ArrayList<BookCharacteristics>();
        allBooks.forEach(b -> {
            BookCharacteristics bookCharacteristics = new BookCharacteristics(b);
            bookCharacteristicsList.add(bookCharacteristics);
            kieSession.insert(bookCharacteristics);
            kieSession.fireAllRules();
        });
        kieSession.dispose();
        bookCharacteristicsList.sort((b1, b2) -> {
            if (b1.isSuggested()) return -1;
            if (b2.isSuggested()) return  1;
            return 0;
        });
        return bookCharacteristicsList;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}
