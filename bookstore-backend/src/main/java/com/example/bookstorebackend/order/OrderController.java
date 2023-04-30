package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.BookService;
import com.example.bookstorebackend.exceptions.EmailExistsException;
import com.example.bookstorebackend.person.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/bookstore/order")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/discount")
    public ResponseEntity<?> GetPriceWithDiscount(@RequestBody OrderDTO order) {
        try {
            double finalPrice = orderService.calculateTotalPrice(order);
            return new ResponseEntity<>(finalPrice,OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }
}
