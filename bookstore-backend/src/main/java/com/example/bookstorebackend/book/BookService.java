package com.example.bookstorebackend.book;

import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.person.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Book> getRecommendedBooks(){
        PriorityQueue<Author> popularAuthors = new PriorityQueue<>(10,
                Comparator.comparingDouble(Author::getTotalRatingNumber));
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
            System.out.println(book.getTitle() + ": " + book.getTotalRatingNumber());
        for (Author author : popularAuthors)
            System.out.println(author.getName() + ": " + author.getTotalRatingNumber());

        kieSession.dispose();
        return recommendedBooks;
    }
    
    public void save(Book book) {
        bookRepository.save(book);
    }
}
