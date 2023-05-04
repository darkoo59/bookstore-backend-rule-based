package com.example.bookstorebackend.order;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.BookService;
import com.example.bookstorebackend.order.dto.*;
import com.example.bookstorebackend.order.model.Order;
import com.example.bookstorebackend.orderItem.OrderItem;
import com.example.bookstorebackend.person.service.UserService;
import com.example.bookstorebackend.utils.enums.BookGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final BookService bookService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Autowired
    public OrderService(BookService bookService, OrderRepository orderRepository, UserService userService) {
        this.bookService = bookService;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }
    public Discount calculateItemDiscount(ItemDTO item) {
        Discount discount = new Discount(0.0,"");
        if (item.getQuantity() >= 2) {
            double discountAmount = item.getPrice() * item.getQuantity() * 0.1;
            discount.setDiscountAmount(discountAmount);
            String responseMessage = "Applied a discount of 10%("+df.format(discountAmount)+" RSD) for ordering 2 or more items of "+bookService.getById(item.getBookId()).getTitle()+".";
            discount.setMessage(responseMessage);
            return discount;
        }
        if (bookService.getById(item.getBookId()).getPrice()*item.getQuantity() > 2000 && bookService.getById(item.getBookId()).getGenre() == BookGenre.EDUCATIONAL) {
            double discountAmount = item.getPrice() * item.getQuantity() * 0.07;
            discount.setDiscountAmount(discountAmount);
            String responseMessage = "Applied a discount of 7%("+df.format(discountAmount)+" RSD) for ordering Educational book("+bookService.getById(item.getBookId()).getTitle()+") of value greater than 2000 RSD.";
            discount.setMessage(responseMessage);
            return discount;
        }
        if (bookService.getById(item.getBookId()).getPrice()*item.getQuantity() > 3000) {
            double discountAmount = item.getPrice() * item.getQuantity() * 0.05;
            discount.setDiscountAmount(discountAmount);
            String responseMessage = "Applied a discount of 5%("+df.format(discountAmount)+" RSD) for ordering "+bookService.getById(item.getBookId()).getTitle()+" which value is greater than 3000 RSD.";
            discount.setMessage(responseMessage);
            return discount;
        }
        return discount;
    }

    public Discount calculateOrderDiscount(OrderDTO order) {
        int itemCount = order.getItems().size();
        Discount discount = new Discount(0.0,"");
        if (itemCount >= 5) {
            double discountAmount = order.getItems().stream()
                    .mapToDouble(ItemDTO::getPrice)
                    .sum() * 0.15;
            String responseMessage = "Applied a discount of 15%("+df.format(discountAmount)+" RSD) for ordering more than 5 items.";
            discount.setDiscountAmount(discountAmount);
            discount.setMessage(responseMessage);
            return discount;
        }
        if (itemCount >= 3) {
            double discountAmount = order.getItems().stream()
                    .mapToDouble(ItemDTO::getPrice)
                    .sum() * 0.1;
            String responseMessage = "Applied a discount of 10%("+df.format(discountAmount)+" RSD) for ordering more than 3 items.";
            discount.setDiscountAmount(discountAmount);
            discount.setMessage(responseMessage);
            return discount;
        }

        return discount;
    }

    public DiscountResponseDTO calculateTotalPrice(OrderDTO order) {
        /*double totalPriceWithItemDiscount = order.getItems().stream()
                .mapToDouble(item -> item.getPrice()
                         * item.getQuantity() - calculateItemDiscount(item).getDiscountAmount())
                .sum();*/
        DiscountResponseDTO itemDiscount = new DiscountResponseDTO(0.0,new ArrayList<>());
        for (ItemDTO item:order.getItems()) {
            Discount discount = calculateItemDiscount(item);
            itemDiscount.finalPrice += item.getPrice() * item.getQuantity() - discount.getDiscountAmount();
            itemDiscount.message.add(discount.getMessage());
        }
        Discount discount = calculateOrderDiscount(order);
        DiscountResponseDTO orderDiscount = new DiscountResponseDTO(0.0,new ArrayList<>());
        orderDiscount.setFinalPrice(order.getTotalPrice() - discount.getDiscountAmount());
        orderDiscount.message.add(discount.getMessage());
        if (itemDiscount.getFinalPrice() < orderDiscount.getFinalPrice()) {
            return itemDiscount;
        }
        return orderDiscount;
    }

    public void makeDeliveryPaymentOrder(OrderDTO orderDTO, String userEmail) {
        Order order = new Order();
        order.setUser(userService.getUser(userEmail));
        order.setPrice(orderDTO.getTotalPrice());
        List<OrderItem> itemsToAdd = new ArrayList<OrderItem>();
        for (ItemDTO item:orderDTO.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(bookService.getById(item.getBookId()));
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
                myItem.setAuthor(item.getBook().getAuthor());
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
