package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    public List<ItemDTO> items;
    public double totalPrice;
}