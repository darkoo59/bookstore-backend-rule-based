package com.example.bookstorebackend.book.dto;

import com.example.bookstorebackend.genre.dto.GenreDTO;
import com.example.bookstorebackend.grade.dto.GradeDTO;
import com.example.bookstorebackend.person.dto.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private AuthorDTO author;

    private String publisher;
    private GenreDTO genre;
    private List<GradeDTO> grades;
    private int numberOfPages;
    private double price;
}
