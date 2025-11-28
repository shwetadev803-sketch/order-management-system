package com.demo.mgmt.order.Controller;

import com.demo.mgmt.order.Entity.Order;
import com.demo.mgmt.order.Entity.OrderStatus;
import com.demo.mgmt.order.Model.OrderDto;
import com.demo.mgmt.order.Model.OrderResponse;
import com.demo.mgmt.order.Service.OrderService;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderDto orderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> orderById(@PathVariable long id){
        return ResponseEntity.ok(orderService.orderById(id));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> orders(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(required = false)OrderStatus status,
            @RequestParam(defaultValue = "createdAt,desc")
            String[] sort
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort[1]),sort[0]);
         Page<OrderResponse> orders = orderService.allOrders(pageable,status);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("modify/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@Valid @RequestBody OrderDto orderDto,@PathVariable long id){
        Order updatedOrder = orderService.updateOrder(orderDto,id);
        OrderResponse response = new OrderResponse(
                updatedOrder.getId(),
                updatedOrder.getProductName(),
                updatedOrder.getQuantity(),
                updatedOrder.getPrice(),
                updatedOrder.getStatus(),
                updatedOrder.getCreatedAt()
        );
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
