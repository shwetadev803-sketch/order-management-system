package com.demo.mgmt.order.Service;


import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Model.OrderDto;
import com.demo.mgmt.order.Model.OrderResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface OrderService {
     OrderResponse createOrder(OrderDto order);

     Order orderById(long id);

    @Nullable List<Order> allOrders();

    @Nullable Order updateOrder(OrderDto orderDto,long id);

    void deleteOrder(Long id);
}
