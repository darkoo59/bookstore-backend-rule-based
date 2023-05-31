package com.example.bookstorebackend.book;

import com.example.bookstorebackend.BookstoreBackendApplication;
import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.person.repository.AuthorRepository;
import com.example.bookstorebackend.person.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final KieContainer kieContainer;
    private final UserRepository userRepository;

    public List<Book> getAll() { return this.bookRepository.findAll(); }
    public Book getById(long id) { return this.bookRepository.findById(id).get(); }

    public void validateBookPrice(Book book) {
        KieSession kiesession = kieContainer.newKieSession();
        kiesession.insert(book);
        kiesession.fireAllRules();
        kiesession.dispose();
    }

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
        kieSession.getAgenda().getAgendaGroup("remove books").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        bookCharacteristicsList.sort((b1, b2) -> {
              if (b1.getBook().getAverageRating() - b2.getBook().getAverageRating() > 0) return -1;
              if (b1.getBook().getAverageRating() - b2.getBook().getAverageRating() < 0) return 1;
              return 0;
        });
        return bookCharacteristicsList;
    }

    public List<Book> getRecommendedBooks(String email) throws Exception {
        Optional<User> specificUser = userRepository.findByEmail(email);
        if(specificUser.isEmpty())
            throw new Exception("No user found");
        List<Genre> genres = specificUser.get().getFavouriteGenres();
        if(genres.isEmpty())
            return new ArrayList<>();
        List<Book> recommendedBooks = new ArrayList<>();
        ConcurrentHashMap<Book, Integer> recommendedBooksWithScores= new ConcurrentHashMap<Book, Integer>();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("recommendedBooks", recommendedBooks);
        kieSession.setGlobal("recommendedBooksWithScores", recommendedBooksWithScores);
        kieSession.setGlobal("specificUser", specificUser.get());
        List<Author> authors = authorRepository.findAll();
        for (Author author : authors)
            kieSession.insert(author);
        for ( Genre genre: genres)
            kieSession.insert(genre);
        List<Book> allBooks = bookRepository.findAll();
        for ( Book book: allBooks) {
            kieSession.insert(book);
        }
        List<User> allUsers = userRepository.findAll();
        for ( User user: allUsers) {
            kieSession.insert(user);
        }
        kieSession.fireAllRules();

        for (Book book : recommendedBooks){
            System.out.println("##");
            System.out.println(book.getTitle() + ": " + book.getTotalRatingNumber());
            System.out.println("Author: " + book.getAuthor().getName());
            System.out.println("##");
        }
        System.out.println("Na kraju ima knjiga : "+ recommendedBooks.size());
        System.out.println("*************");

        kieSession.dispose();
        return recommendedBooks;
    }
    
    public void save(Book book) {
        bookRepository.save(book);
    }
}
