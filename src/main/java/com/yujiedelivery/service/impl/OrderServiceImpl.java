package com.yujiedelivery.service.impl;

import com.yujiedelivery.exception.BadRequestException;
import com.yujiedelivery.exception.ResourceNotFoundException;
import com.yujiedelivery.model.Order;
import com.yujiedelivery.model.Order.OrderStatus;
import com.yujiedelivery.model.User;
import com.yujiedelivery.repository.OrderRepository;
import com.yujiedelivery.service.OrderService;
import com.yujiedelivery.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        order.setUser(userService.getCurrentUser());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Order> getOrdersByDeliveryPerson(Long deliveryPersonId) {
        return orderRepository.findByDeliveryPersonId(deliveryPersonId);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getRestaurantOrdersByStatus(Long restaurantId, OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
    }

    @Override
    public List<Order> getDeliveryPersonOrdersByStatus(Long deliveryPersonId, OrderStatus status) {
        return orderRepository.findByDeliveryPersonIdAndStatus(deliveryPersonId, status);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        
        // 验证状态转换是否合法
        validateStatusTransition(order.getStatus(), status);
        
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order assignDeliveryPerson(Long orderId, Long deliveryPersonId) {
        Order order = getOrderById(orderId);
        User deliveryPerson = userService.getUserById(deliveryPersonId);
        
        if (deliveryPerson.getRole() != User.UserRole.DELIVERY_PERSON) {
            throw new BadRequestException("User is not a delivery person");
        }
        
        order.setDeliveryPerson(deliveryPerson);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // 定义合法的状态转换
        switch (currentStatus) {
            case PENDING:
                if (newStatus != OrderStatus.CONFIRMED && newStatus != OrderStatus.CANCELLED) {
                    throw new BadRequestException("Invalid status transition from PENDING");
                }
                break;
            case CONFIRMED:
                if (newStatus != OrderStatus.PREPARING && newStatus != OrderStatus.CANCELLED) {
                    throw new BadRequestException("Invalid status transition from CONFIRMED");
                }
                break;
            case PREPARING:
                if (newStatus != OrderStatus.READY_FOR_DELIVERY && newStatus != OrderStatus.CANCELLED) {
                    throw new BadRequestException("Invalid status transition from PREPARING");
                }
                break;
            case READY_FOR_DELIVERY:
                if (newStatus != OrderStatus.OUT_FOR_DELIVERY && newStatus != OrderStatus.CANCELLED) {
                    throw new BadRequestException("Invalid status transition from READY_FOR_DELIVERY");
                }
                break;
            case OUT_FOR_DELIVERY:
                if (newStatus != OrderStatus.DELIVERED && newStatus != OrderStatus.CANCELLED) {
                    throw new BadRequestException("Invalid status transition from OUT_FOR_DELIVERY");
                }
                break;
            case DELIVERED:
            case CANCELLED:
                throw new BadRequestException("Cannot change status of " + currentStatus + " order");
        }
    }
} 