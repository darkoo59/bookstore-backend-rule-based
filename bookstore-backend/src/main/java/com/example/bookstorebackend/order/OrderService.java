package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.BookService;
import com.example.bookstorebackend.utils.enums.BookGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final BookService bookService;
    @Autowired
    public OrderService(BookService bookService) {
        this.bookService = bookService;
    }
    public double calculateItemDiscount(ItemDTO item) {
        if (item.getQuantity() >= 2) {
            return item.getPrice() * item.getQuantity() * 0.1;
        }
        if (bookService.getById(item.getBookId()).getPrice()*item.getQuantity() > 2000 && bookService.getById(item.getBookId()).getGenre() == BookGenre.EDUCATIONAL) {
            return item.getPrice() * item.getQuantity() * 0.07;
        }
        if (bookService.getById(item.getBookId()).getPrice()*item.getQuantity() > 3000) {
            return item.getPrice() * item.getQuantity() * 0.05;
        }
        return 0.0;
    }

    public double calculateOrderDiscount(OrderDTO order) {
        int itemCount = order.getItems().size();
        if (itemCount >= 5) {
            return order.getItems().stream()
                    .mapToDouble(ItemDTO::getPrice)
                    .sum() * 0.15;
        }
        if (itemCount >= 3) {
            return order.getItems().stream()
                    .mapToDouble(ItemDTO::getPrice)
                    .sum() * 0.1;
        }

        return 0.0;
    }

    public double calculateTotalPrice(OrderDTO order) {
        double totalPriceWithItemDiscount = order.getItems().stream()
                .mapToDouble(item -> item.getPrice()
                         * item.getQuantity() - calculateItemDiscount(item))
                .sum();
        double totalPriceWithOrderDiscount = order.getTotalPrice() - calculateOrderDiscount(order);
        if (totalPriceWithItemDiscount < totalPriceWithOrderDiscount) {
            return totalPriceWithItemDiscount;
        }
        return totalPriceWithOrderDiscount;
    }
}
