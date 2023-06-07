package by.teachmeskills.springbootexample.services.impl;

import by.teachmeskills.springbootexample.entities.*;
import by.teachmeskills.springbootexample.repositories.OrderDetailsRepository;
import by.teachmeskills.springbootexample.repositories.OrderRepository;
import by.teachmeskills.springbootexample.repositories.ProductRepository;
import by.teachmeskills.springbootexample.repositories.UserRepository;
import by.teachmeskills.springbootexample.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public void createOrder() {
        User user = userRepository.findByEmailAndPassword("ivan.ivanov@gmail.com", "ivan_12345");

        Order order = Order.builder()
                .user(user)
                .date(LocalDateTime.now())
                .price(15000)
                .build();

        Product atlant = productRepository.findById(3);

        OrderDetailsId orderDetailsId = OrderDetailsId.builder()
                .order(order)
                .product(atlant)
                .build();

        OrderDetails orderDetails = OrderDetails.builder()
                .orderDetailsId(orderDetailsId)
                .quantity(10)
                .build();

        orderRepository.createOrder(order);
        orderDetailsRepository.createOrderDetails(orderDetails);
    }
}
