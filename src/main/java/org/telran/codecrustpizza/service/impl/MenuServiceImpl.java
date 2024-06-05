package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.menu.MenuItemResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.enums.MenuCategory;
import org.telran.codecrustpizza.service.ItemService;
import org.telran.codecrustpizza.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.telran.codecrustpizza.entity.enums.MenuCategory.PIZZAS;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final PizzaPatternServiceImpl pizzaPatternService;
    private final ItemService itemService;

    @Override
    public Map<MenuCategory, List<MenuItemResponseDto>> getMenu() {

        List<PizzaPatternResponseDto> pizzaPatterns = pizzaPatternService.findAll();

        Map<MenuCategory, List<MenuItemResponseDto>> itemDtoGroupedByCategory = itemService.getAllForMenu();

        itemDtoGroupedByCategory.put(PIZZAS, pizzaDtoToMenuItemDto(pizzaPatterns));

        return itemDtoGroupedByCategory;
    }

    private List<MenuItemResponseDto> pizzaDtoToMenuItemDto(List<PizzaPatternResponseDto> pizzaPatterns) {
        List<MenuItemResponseDto> itemDtoList = new ArrayList<>();

        for (PizzaPatternResponseDto pizzaDto : pizzaPatterns) {

            itemDtoList.add(new MenuItemResponseDto(
                    pizzaDto.title(),
                    pizzaDto.description(),
                    pizzaDto.price()
            ));
        }

        return itemDtoList;
    }
}