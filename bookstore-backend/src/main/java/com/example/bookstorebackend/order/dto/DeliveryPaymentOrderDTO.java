package com.example.bookstorebackend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPaymentOrderDTO {
   public double totalPrice;
   public List<ItemDTO> items;
}
