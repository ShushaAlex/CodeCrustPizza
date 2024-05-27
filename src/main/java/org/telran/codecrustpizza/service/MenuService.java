package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.menu.MenuResponseDto;

/**
 * Service interface for retrieving menu.
 */
public interface MenuService {

    /**
     * Retrieves the current menu.
     *
     * @return a {@link MenuResponseDto} representing the current menu.
     */
    MenuResponseDto getMenu();
}
