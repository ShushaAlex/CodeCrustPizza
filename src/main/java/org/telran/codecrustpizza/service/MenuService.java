package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.menu.MenuItemResponseDto;
import org.telran.codecrustpizza.entity.enums.MenuCategory;

import java.util.List;
import java.util.Map;

/**
 * Service interface for retrieving menu.
 */
public interface MenuService {

    /**
     * Retrieves the menu containing various categories of items.
     *
     * @return A map representing the menu categories with their corresponding items.
     *         The map keys are the menu category names (MenuCategory enum), and the values are lists of MenuItemResponseDto objects.
     */
    Map<MenuCategory, List<MenuItemResponseDto>> getMenu();
}