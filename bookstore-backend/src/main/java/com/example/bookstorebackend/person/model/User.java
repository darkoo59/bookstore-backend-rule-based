package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.orderItem.OrderItem;
import jakarta.persistence.*;
import com.example.bookstorebackend.rating.model.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "user_")
@NoArgsConstructor
@Setter
@Getter
public class User extends Person{
    @ManyToMany
    private List<Genre> favouriteGenres;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Rating> ratings;
}
