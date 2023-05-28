package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.BookService;
import com.example.bookstorebackend.order.dto.*;
import com.example.bookstorebackend.order.model.Order;
import com.example.bookstorebackend.orderItem.OrderItem;
import com.example.bookstorebackend.person.service.UserService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final BookService bookService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final KieContainer kieContainer;

    public DiscountResponseDTO getPriceWithDiscount(OrderDTO order) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
        return new DiscountResponseDTO(order.totalPrice, order.discountReason);
    }

    public void makeDeliveryPaymentOrder(OrderDTO orderDTO, String userEmail) {
        Order order = new Order();
        order.setUser(userService.getUser(userEmail));
        order.setPrice(orderDTO.getTotalPrice());
        List<OrderItem> itemsToAdd = new ArrayList<OrderItem>();
        for (ItemDTO item:orderDTO.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(bookService.getById(item.getBook().getId()));
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            itemsToAdd.add(orderItem);
        }
        order.setOrderItems(itemsToAdd);
        orderRepository.save(order);
    }

    public List<MyOrdersDTO> getMyOrders(String userEmail) {
        List<MyOrdersDTO> myOrders = new ArrayList<>();
        for (Order order: orderRepository.getOrderByUser(userService.getUser(userEmail))) {
            MyOrdersDTO orderToAdd = new MyOrdersDTO();
            orderToAdd.setId(order.getId());
            orderToAdd.setPrice(order.getPrice());
            List<MyItemDTO> myItems = new ArrayList<>();
            for (OrderItem item:order.getOrderItems()) {
                MyItemDTO myItem = new MyItemDTO();
                myItem.setId(item.getBook().getId());
                myItem.setAuthor(item.getBook().getAuthor().getName());
                myItem.setGenre(item.getBook().getGenre());
                myItem.setPublisher(item.getBook().getPublisher());
                myItem.setPrice(item.getBook().getPrice());
                myItem.setTitle(item.getBook().getTitle());
                myItem.setNumberOfPages(item.getBook().getNumberOfPages());
                myItem.setQuantity(item.getQuantity());
                myItems.add(myItem);
            }
            orderToAdd.setItems(myItems);
            myOrders.add(orderToAdd);
        }
        return myOrders;
    }
}
