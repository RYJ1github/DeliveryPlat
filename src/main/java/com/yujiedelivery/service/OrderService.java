package com.yujiedelivery.service;

import com.yujiedelivery.model.Order;
import com.yujiedelivery.model.Order.OrderStatus;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getOrdersByUser(Long userId);
    List<Order> getOrdersByRestaurant(Long restaurantId);
    List<Order> getOrdersByDeliveryPerson(Long deliveryPersonId);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getRestaurantOrdersByStatus(Long restaurantId, OrderStatus status);
    List<Order> getDeliveryPersonOrdersByStatus(Long deliveryPersonId, OrderStatus status);
    Order updateOrderStatus(Long id, OrderStatus status);
    Order assignDeliveryPerson(Long orderId, Long deliveryPersonId);
    void deleteOrder(Long id);
} 