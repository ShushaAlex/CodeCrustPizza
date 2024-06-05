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
     * Retrieves the current menu.
     *
     * @return a {@link MenuItemResponseDto} representing the current menu.
     */
    Map<MenuCategory, List<MenuItemResponseDto>> getMenu();
}
