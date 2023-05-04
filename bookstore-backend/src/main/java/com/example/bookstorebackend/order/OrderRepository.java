package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.order.model.Order;
import com.example.bookstorebackend.person.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrderByUser(User user);
}
