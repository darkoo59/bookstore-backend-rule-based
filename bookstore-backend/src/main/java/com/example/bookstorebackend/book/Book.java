package com.example.bookstorebackend.book;

import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.grade.Grade;
import com.example.bookstorebackend.person.model.Author;
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
    @OneToMany(mappedBy = "book")
    private List<Grade> grades;
    private int numberOfPages;
    private double price;
    public int getTotalGrades(){
        int grades = 0;
        for(Grade grade: getGrades())
            grades += grade.getValue();
        return grades;
    }
}
