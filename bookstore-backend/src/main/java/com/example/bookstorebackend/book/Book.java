package com.example.bookstorebackend.book;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.rating.model.Rating;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @JsonBackReference(value = "*")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String publisher;

    @JsonBackReference(value = "*")
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    private int numberOfPages;
    private double price;
    private LocalDate dateOfAddingToBookstore;
    private LocalDate publishDate;
    private double averageRating;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private List<Rating> ratings;

    public double getTotalRatingNumber(){
        double ratings = 0;
        for(Rating rating: getRatings())
            ratings += rating.getRating();
        return ratings;
    }

    public boolean areBooksSimilar(Book otherBook) {
        double similarityCount = 0;
        int userCount = 0;
        for (var thisBookRating: this.getRatings()) {
            for (var otherBookRating: otherBook.getRatings()) {
                if (!thisBookRating.getUser().getId().equals(otherBookRating.getUser().getId())) {
                    continue;
                }
                userCount++;
                if (Math.abs(thisBookRating.getRating() - otherBookRating.getRating()) <= 1) {
                    similarityCount++;
                }
            }
        }
        System.out.println("Rezultat = " + similarityCount / userCount);
        return userCount != 0 && similarityCount / userCount >= 0.7;
    }
}
