package com.example.bookstorebackend.book;

import com.example.bookstorebackend.utils.enums.BookGenre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate dateOfAddingToBookstore;
    private LocalDate publishDate;
}
