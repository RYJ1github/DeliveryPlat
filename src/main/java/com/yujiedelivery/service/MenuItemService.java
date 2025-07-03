package com.yujiedelivery.service;

import com.yujiedelivery.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem createMenuItem(MenuItem menuItem, Long restaurantId);
    MenuItem getMenuItemById(Long id);
    List<MenuItem> getMenuItemsByRestaurant(Long restaurantId);
    List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId);
    List<MenuItem> getMenuItemsByCategory(Long restaurantId, String category);
    List<MenuItem> searchMenuItems(Long restaurantId, String keyword);
    MenuItem updateMenuItem(Long id, MenuItem menuItemDetails);
    void deleteMenuItem(Long id);
    MenuItem updateMenuItemAvailability(Long id, boolean isAvailable);
} 