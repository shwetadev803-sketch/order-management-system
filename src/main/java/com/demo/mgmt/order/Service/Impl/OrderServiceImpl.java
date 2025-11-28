package com.demo.mgmt.order.Service.Impl;

import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Entity.OrderStatus;
import com.demo.mgmt.order.Exception.ResourceNotFoundException;
import com.demo.mgmt.order.Model.OrderDto;
import com.demo.mgmt.order.Model.OrderResponse;
import com.demo.mgmt.order.Repository.OrderRepository;
import com.demo.mgmt.order.Service.OrderService;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    public Page<OrderResponse> allOrders(Pageable pageable,OrderStatus orderStatus) {
        Page<Order> orderPage;
        if (orderStatus != null) {
            orderPage = orderRepository.findByStatus(orderStatus,pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }
        Page<OrderResponse> dtoPage = orderPage.map(order -> modelMapper.map(order, OrderResponse.class));//map entity to dto
        return dtoPage;
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
