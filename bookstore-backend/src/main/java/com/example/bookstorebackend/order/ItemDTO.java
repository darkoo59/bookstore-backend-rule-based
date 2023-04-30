package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    public Long bookId;
    public int quantity;
    public double price;
}
