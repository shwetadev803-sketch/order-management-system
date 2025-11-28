package com.demo.mgmt.order.Service.Impl;

import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Entity.OrderStatus;
import com.demo.mgmt.order.Exception.ResourceNotFoundException;
import com.demo.mgmt.order.Model.OrderDto;
import com.demo.mgmt.order.Model.OrderResponse;
import com.demo.mgmt.order.Repository.OrderRepository;
import com.demo.mgmt.order.Service.OrderService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .productName(orderDto.getProductName())
                .quantity(orderDto.getQuantity())
                .price(orderDto.getPrice())
                .status(OrderStatus.CREATED) // default enum value
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.builder()
                .id(savedOrder.getId())
                .productName(savedOrder.getProductName())
                .quantity(savedOrder.getQuantity())
                .price(savedOrder.getPrice())
                .status(savedOrder.getStatus())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }

    @Override
    public Order orderById(long id) {
        return orderRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException("Order not found with id " + id));
    }

    @Override
    public @Nullable List<Order> allOrders() {
        return orderRepository.findAll();
    }

    @Override
    public @Nullable Order updateOrder(OrderDto orderDto, long id) {
        Order orderModify = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Order not found with id " + id)));
        orderModify.setProductName(orderDto.getProductName());
        orderModify.setQuantity(orderDto.getQuantity());
        orderModify.setPrice(orderDto.getPrice());
        orderRepository.save(orderModify);

        return orderModify;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

}
