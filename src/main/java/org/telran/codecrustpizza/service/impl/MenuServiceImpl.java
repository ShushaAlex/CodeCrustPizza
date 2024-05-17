package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.dto.menu.MenuResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.service.ItemService;
import org.telran.codecrustpizza.service.MenuService;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final PizzaPatternServiceImpl pizzaPatternService;
    private final ItemService itemService;

    @Autowired
    public MenuServiceImpl(PizzaPatternServiceImpl pizzaPatternService, ItemService itemService) {
        this.pizzaPatternService = pizzaPatternService;
        this.itemService = itemService;
    }

    @Override
    public MenuResponseDto getMenu() {
        List<PizzaPatternResponseDto> pizzaPatterns = pizzaPatternService.findAll();
        List<ItemResponseDto> items = itemService.getAll();
        return new MenuResponseDto(pizzaPatterns, items);
    }
}
