package com.yujiedelivery.controller;

import com.yujiedelivery.model.Order;
import com.yujiedelivery.model.Order.OrderStatus;
import com.yujiedelivery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Order Management", description = "Order management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create order", description = "Creates a new order (User only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order created successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER') or hasRole('DELIVERY_PERSON') or hasRole('USER')")
    @Operation(summary = "Get order by ID", description = "Retrieves an order's information by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order information retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @Operation(summary = "Get orders by user", description = "Retrieves all orders for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Get orders by restaurant", description = "Retrieves all orders for a specific restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId));
    }

    @GetMapping("/delivery-person/{deliveryPersonId}")
    @PreAuthorize("hasRole('ADMIN') or #deliveryPersonId == authentication.principal.id")
    @Operation(summary = "Get orders by delivery person", description = "Retrieves all orders assigned to a specific delivery person")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getOrdersByDeliveryPerson(@PathVariable Long deliveryPersonId) {
        return ResponseEntity.ok(orderService.getOrdersByDeliveryPerson(deliveryPersonId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER') or hasRole('DELIVERY_PERSON')")
    @Operation(summary = "Get orders by status", description = "Retrieves orders based on their status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/restaurant/{restaurantId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Get restaurant orders by status", description = "Retrieves orders for a restaurant based on their status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getRestaurantOrdersByStatus(@PathVariable Long restaurantId, @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getRestaurantOrdersByStatus(restaurantId, status));
    }

    @GetMapping("/delivery-person/{deliveryPersonId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or #deliveryPersonId == authentication.principal.id")
    @Operation(summary = "Get delivery person orders by status", description = "Retrieves orders for a delivery person based on their status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<Order>> getDeliveryPersonOrdersByStatus(@PathVariable Long deliveryPersonId, @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getDeliveryPersonOrdersByStatus(deliveryPersonId, status));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER') or hasRole('DELIVERY_PERSON')")
    @Operation(summary = "Update order status", description = "Updates the status of an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid status transition")
    })
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PutMapping("/{id}/assign-delivery")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @Operation(summary = "Assign delivery person", description = "Assigns a delivery person to an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delivery person assigned successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid delivery person")
    })
    public ResponseEntity<Order> assignDeliveryPerson(@PathVariable Long id, @RequestParam Long deliveryPersonId) {
        return ResponseEntity.ok(orderService.assignDeliveryPerson(id, deliveryPersonId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Delete order", description = "Deletes an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
} 