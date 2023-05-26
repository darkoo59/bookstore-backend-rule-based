package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.grade.Grade;
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
    public int getTotalGrades(){
        int grades = 0;
        for(Book book: getBooks())
            grades += book.getTotalGrades();
        return grades;
    }
}
