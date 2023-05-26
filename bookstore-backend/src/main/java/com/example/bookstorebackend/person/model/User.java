package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.orderItem.OrderItem;
import jakarta.persistence.*;
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
}
