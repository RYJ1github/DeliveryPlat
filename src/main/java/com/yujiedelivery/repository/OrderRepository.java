package com.yujiedelivery.repository;

import com.yujiedelivery.model.Order;
import com.yujiedelivery.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByDeliveryPersonId(Long deliveryPersonId);
    List<Order> findByStatus(OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = :status")
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.deliveryPerson.id = :deliveryPersonId AND o.status = :status")
    List<Order> findByDeliveryPersonIdAndStatus(Long deliveryPersonId, OrderStatus status);
} 