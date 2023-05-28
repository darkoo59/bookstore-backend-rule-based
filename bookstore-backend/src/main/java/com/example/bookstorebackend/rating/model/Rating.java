package com.example.bookstorebackend.rating.model;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.person.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonBackReference(value = "*")
    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private double rating;

    @JsonBackReference(value = "*")
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
