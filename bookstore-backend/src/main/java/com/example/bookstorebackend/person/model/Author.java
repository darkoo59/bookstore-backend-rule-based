package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.genre.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String name;
    @OneToMany(mappedBy = "author")
    private List<Book> books;
    @Transient
    private boolean isPopular;
    @Transient
    private List<Genre> genres = new ArrayList<>();

    public Author(String name){
        this.name = name;
    }
    public double getTotalRatingNumber(){
        double ratings = 0;
        for(Book book: getBooks())
            ratings += book.getTotalRatingNumber();
        return ratings;
    }

    public int getBookNumberFromGenre(Genre genre) {
        int count = 0;
        for(Book book: getBooks()){
            if(book.getGenre().getId().equals(genre.getId()))
                count++;
        }
        return count;
    }

    public void addGenre(Genre genre){
        this.genres.add(genre);
    }

    public int getBooksLength(){
        return this.books.size();
    }

    public int getGenresLength(){
        return this.genres.size();
    }

    public int getBookNumber() {
        return this.getBooks().size();
    }
}
