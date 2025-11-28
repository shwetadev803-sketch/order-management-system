package com.demo.mgmt.order.Service;


import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Entity.OrderStatus;
import com.demo.mgmt.order.Model.OrderDto;
import com.demo.mgmt.order.Model.OrderResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
     OrderResponse createOrder(OrderDto order);

     Order orderById(long id);

    @Nullable Page<OrderResponse> allOrders(Pageable pageable, OrderStatus response);

    @Nullable Order updateOrder(OrderDto orderDto,long id);

    void deleteOrder(Long id);
}
