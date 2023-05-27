package com.example.bookstorebackend.order.dto;

import com.example.bookstorebackend.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    public Book book;
    public int quantity;
    public double price;
    public int discount;
}
