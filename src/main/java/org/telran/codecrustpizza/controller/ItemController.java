package org.telran.codecrustpizza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.telran.codecrustpizza.service.ItemService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponseDto> getAll() {

        return itemService.getAll();
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto getById(@PathVariable Long itemId) {

        return itemService.findById(itemId);
    }

    @GetMapping("/category")
    public List<ItemResponseDto> findItemsByCategory(@RequestParam String category) {

        return itemService.findAllByCategory(category);
    }

    @PostMapping
    public ItemResponseDto saveItem(@Valid @RequestBody ItemCreateRequestDto createDto) {

        return itemService.saveItem(createDto);
    }

    @PutMapping("/{itemId}/update")
    public ItemResponseDto updateItem(@PathVariable Long itemId, @Valid ItemCreateRequestDto createRequestDto) {

        return itemService.updateItem(itemId, createRequestDto);
    }

    @PostMapping("/{itemId}/category")
    public ItemResponseDto addCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.addCategory(itemId, category);
    }

    @DeleteMapping("/{itemId}/category")
    public ItemResponseDto removeCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.removeCategory(itemId, category);
    }

    @DeleteMapping("/{itemId}")
    public boolean deleteItem(@PathVariable Long itemId) {

        return itemService.deleteItem(itemId);
    }

}
