package com.example.bookstorebackend.book;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.rating.model.Rating;
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
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    private int numberOfPages;
    private double price;
    private double averageRating;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "book")
    private List<Rating> ratings;

    public double getTotalRatingNumber(){
        double ratings = 0;
        for(Rating rating: getRatings())
            ratings += rating.getRating();
        return ratings;
    }
}
