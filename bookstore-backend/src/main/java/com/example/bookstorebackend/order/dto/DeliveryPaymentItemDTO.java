package com.example.bookstorebackend.order.dto;

import lombok.Data;

@Data
public class DeliveryPaymentItemDTO {
    public int bookId;
    public double price;
    public int quantity;
}
