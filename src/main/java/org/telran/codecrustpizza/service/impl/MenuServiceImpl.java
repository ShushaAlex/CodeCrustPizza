package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.menu.MenuResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.service.ItemService;
import org.telran.codecrustpizza.service.MenuService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final PizzaPatternServiceImpl pizzaPatternService;
    private final ItemService itemService;

    @Override
    public MenuResponseDto getMenu() {
        List<PizzaPatternResponseDto> pizzaPatterns = pizzaPatternService.findAll();

        return null;
    }
}