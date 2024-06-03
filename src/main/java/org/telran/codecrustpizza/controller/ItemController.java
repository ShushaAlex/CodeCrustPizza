package org.telran.codecrustpizza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.dto.menu.MenuResponseDto;
import org.telran.codecrustpizza.service.ItemService;
import org.telran.codecrustpizza.service.MenuService;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MenuService menuService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ItemResponseDto> getAll() {

        return itemService.getAll();
    }

    @GetMapping("/menu")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public MenuResponseDto getMenu() {

        return menuService.getMenu();
    }

    @GetMapping("/{itemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ItemResponseDto getById(@PathVariable Long itemId) {

        return itemService.getItemDtoById(itemId);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ItemResponseDto> findItemsByCategory(@RequestParam String category) {

        return itemService.findAllByCategory(category);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto saveItem(@Valid @RequestBody ItemCreateRequestDto createDto) {

        return itemService.saveItem(createDto);
    }

    @PutMapping("/update/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemCreateRequestDto createRequestDto) {

        return itemService.updateItem(itemId, createRequestDto);
    }

    @PostMapping("/{itemId}/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto addCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.addCategory(itemId, category);
    }

    @DeleteMapping("/{itemId}/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto removeCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.removeCategory(itemId, category);
    }

}