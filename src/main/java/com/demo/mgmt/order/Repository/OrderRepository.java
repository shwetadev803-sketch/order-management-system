package com.demo.mgmt.order.Repository;

import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Entity.OrderStatus;
import com.demo.mgmt.order.Model.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByStatus(OrderStatus orderStatus, Pageable pageable);
}
