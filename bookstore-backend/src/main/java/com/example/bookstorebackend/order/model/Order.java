package com.example.bookstorebackend.order.model;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.orderItem.OrderItem;
import com.example.bookstorebackend.person.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "order_")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private Double price;

    private int discount;
}
