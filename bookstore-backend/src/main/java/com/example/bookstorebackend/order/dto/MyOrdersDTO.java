package com.example.bookstorebackend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrdersDTO {
    public Long id;
    public List<MyItemDTO> items;
    public double price;
}
