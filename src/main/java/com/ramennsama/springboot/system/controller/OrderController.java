package com.ramennsama.springboot.system.controller;

import com.ramennsama.springboot.system.dto.request.OrderRequest;
import com.ramennsama.springboot.system.dto.response.OrderResponse;
import com.ramennsama.springboot.system.entity.enumtype.OrderStatus;
import com.ramennsama.springboot.system.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders in E-Commerce system")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Returns a complete list of all orders in the system"
    )
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Returns detailed information of an order based on ID"
    )
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID of the order to find", required = true)
            @PathVariable Long id) {
        OrderResponse order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get orders by user ID",
            description = "Returns all orders for a specific user"
    )
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        List<OrderResponse> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Get orders by status",
            description = "Returns all orders with a specific status"
    )
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Order status (PENDING, PAID)", required = true)
            @PathVariable OrderStatus status) {
        List<OrderResponse> orders = orderService.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @Operation(
            summary = "Create new order",
            description = "Creates a new order in the system"
    )
    public ResponseEntity<OrderResponse> createOrder(
            @Parameter(description = "Order information to create", required = true)
            @RequestBody OrderRequest orderRequest) {
        OrderResponse createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update order status",
            description = "Updates the status of an existing order"
    )
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "ID of the order to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New status (PENDING, PAID)", required = true)
            @RequestParam OrderStatus status) {
        OrderResponse updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
}
