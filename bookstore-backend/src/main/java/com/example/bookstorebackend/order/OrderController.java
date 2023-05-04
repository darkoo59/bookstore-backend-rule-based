package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.order.dto.DiscountResponseDTO;
import com.example.bookstorebackend.order.dto.MyOrdersDTO;
import com.example.bookstorebackend.order.dto.OrderDTO;
import com.example.bookstorebackend.security.filter.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
    @Secured("ROLE_USER")
    public ResponseEntity<?> GetPriceWithDiscount(@RequestBody OrderDTO order) {
        try {
            DiscountResponseDTO response = orderService.calculateTotalPrice(order);
            return new ResponseEntity<>(response,OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }

    @PostMapping("/delivery-payment")
    @Secured("ROLE_USER")
    public ResponseEntity<?> MakeDeliveryPaymentOrder(@RequestBody OrderDTO order, HttpServletRequest request) {
        try {
            orderService.makeDeliveryPaymentOrder(order, AuthUtility.getEmailFromRequest(request));
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }

    @GetMapping("/my-orders")
    @Secured("ROLE_USER")
    public ResponseEntity<List<MyOrdersDTO>> getMyOrders(HttpServletRequest request){
        return new ResponseEntity<>(orderService.getMyOrders(AuthUtility.getEmailFromRequest(request)), OK);
    }
}
