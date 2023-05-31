package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.orderItem.OrderItem;
import jakarta.persistence.*;
import com.example.bookstorebackend.rating.model.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user_")
@NoArgsConstructor
@Setter
@Getter
@Data
public class User extends Person{
    @ManyToMany
    private List<Genre> favouriteGenres;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Rating> ratings;

    public int getRatingsNumber() {
        return this.getRatings().size();
    }

    public double calculateSimilarity(User user2) {
        List<Rating> ratings1 = getRatings();
        List<Rating> ratings2 = user2.getRatings();

        // Pronalaženje zajedničkih knjiga ocenjenih od strane oba korisnika
        List<String> commonBooks = ratings1.stream()
                .map(Rating::getBook)
                .filter(book1 -> ratings2.stream().anyMatch(rating2 -> rating2.getBook().getId() == book1.getId()))
                .map(Book::getId)
                .map(Object::toString)
                .collect(Collectors.toList());

        // Izračunavanje srednjih vrednosti ocena za oba korisnika
        double mean1 = calculateMeanRating(ratings1);
        double mean2 = calculateMeanRating(ratings2);

        // Izračunavanje brojnika i imenioca za Pirsonov koeficijent korelacije
        double numerator = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;

        for (String bookId : commonBooks) {
            double rating1 = getRatingForBook(this, bookId);
            double rating2 = getRatingForBook(user2, bookId);

            numerator += (rating1 - mean1) * (rating2 - mean2);
            denominator1 += Math.pow(rating1 - mean1, 2);
            denominator2 += Math.pow(rating2 - mean2, 2);
        }

        // Izračunavanje Pirsonovog koeficijenta korelacije
        double similarity = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        return similarity;
    }

    private double calculateMeanRating(List<Rating> ratings) {
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }

    private double getRatingForBook(User user, String bookId) {
        return user.getRatings().stream()
                .filter(rating -> rating.getBook().getId().toString().equals(bookId))
                .findFirst()
                .map(Rating::getRating)
                .orElse(0.0);
    }

    public List<Rating> getRatingsGreaterThenFour(){
        List<Rating> ratingsToReturn = new ArrayList<>();
        for(Rating rating: ratings) {
            if(rating.getRating() >= 4.0)
                ratingsToReturn.add(rating);
        }

        return ratingsToReturn;
    }
}
