package com.example.bookstorebackend.book;

import com.example.bookstorebackend.rating.model.Rating;
import com.example.bookstorebackend.utils.enums.BookGenre;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private BookGenre genre;
    private int numberOfPages;
    private double price;
    private double averageRating;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    @JsonIgnore
    private List<Rating> ratings;
}
