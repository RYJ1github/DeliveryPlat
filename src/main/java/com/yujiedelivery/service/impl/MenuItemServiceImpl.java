package com.yujiedelivery.service.impl;

import com.yujiedelivery.exception.ResourceNotFoundException;
import com.yujiedelivery.model.MenuItem;
import com.yujiedelivery.model.Restaurant;
import com.yujiedelivery.repository.MenuItemRepository;
import com.yujiedelivery.service.MenuItemService;
import com.yujiedelivery.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    @Transactional
    public MenuItem createMenuItem(MenuItem menuItem, Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "id", id));
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndIsAvailableTrue(restaurantId);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(Long restaurantId, String category) {
        return menuItemRepository.findByRestaurantIdAndCategory(restaurantId, category);
    }

    @Override
    public List<MenuItem> searchMenuItems(Long restaurantId, String keyword) {
        return menuItemRepository.searchMenuItems(restaurantId, keyword);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = getMenuItemById(id);
        
        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setImageUrl(menuItemDetails.getImageUrl());
        menuItem.setPreparationTime(menuItemDetails.getPreparationTime());
        
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = getMenuItemById(id);
        menuItemRepository.delete(menuItem);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItemAvailability(Long id, boolean isAvailable) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setIsAvailable(isAvailable);
        return menuItemRepository.save(menuItem);
    }
} 