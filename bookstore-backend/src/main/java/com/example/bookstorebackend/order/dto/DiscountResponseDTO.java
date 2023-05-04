package com.example.bookstorebackend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponseDTO {
    public double finalPrice;
    public List<String> message;
}
