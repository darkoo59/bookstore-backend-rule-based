package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.book.Book;
import jakarta.persistence.*;
import lombok.*;

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
    public Author(String name){
        this.name = name;
    }
    public int getTotalRatingNumber(){
        int ratings = 0;
        for(Book book: getBooks())
            ratings += book.getTotalRatingNumber();
        return ratings;
    }
}
