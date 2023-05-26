package com.example.bookstorebackend.order.dto;

import com.example.bookstorebackend.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyItemDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Genre genre;
    private int numberOfPages;
    private double price;
    private int quantity;
}
