package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.telran.codecrustpizza.dto.menu.MenuItemResponseDto;
import org.telran.codecrustpizza.entity.enums.MenuCategory;
import org.telran.codecrustpizza.service.ItemService;
import org.telran.codecrustpizza.service.MenuService;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("api/item")
@RequiredArgsConstructor
@Tag(name = "Item Controller", description = "Operations related to item management")
public class ItemController {

    private final ItemService itemService;
    private final MenuService menuService;

    @Operation(summary = "Get all items", description = "Retrieve a list of all items.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of items")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ItemResponseDto> getAll() {

        return itemService.getAll();
    }

    @Operation(summary = "Get menu", description = "Retrieve the menu categorized by menu categories.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved menu")
    @GetMapping("/menu")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Map<MenuCategory, List<MenuItemResponseDto>> getMenu() {

        return menuService.getMenu();
    }

    @Operation(summary = "Get item by ID", description = "Retrieve a specific item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved item"),
            @ApiResponse(responseCode = "400", description = "Item not found")
    })
    @GetMapping("/{itemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ItemResponseDto getById(@PathVariable Long itemId) {

        return itemService.getItemDtoById(itemId);
    }

    @Operation(summary = "Find items by category", description = "Retrieve items based on category.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved items by category")
    @GetMapping("/category")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ItemResponseDto> findItemsByCategory(@RequestParam String category) {

        return itemService.findAllByCategory(category);
    }

    @Operation(summary = "Save new item", description = "Save a new item. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved item"),
            @ApiResponse(responseCode = "400", description = "Item already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto saveItem(@Valid @RequestBody ItemCreateRequestDto createDto) {

        return itemService.saveItem(createDto);
    }

    @Operation(summary = "Update item", description = "Update an existing item by its ID. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated item"),
            @ApiResponse(responseCode = "400", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/update/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemCreateRequestDto createRequestDto) {

        return itemService.updateItem(itemId, createRequestDto);
    }

    @Operation(summary = "Add category to item", description = "Add a category to an existing item. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added category to item"),
            @ApiResponse(responseCode = "400", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping("/{itemId}/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto addCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.addCategory(itemId, category);
    }

    @Operation(summary = "Remove category from item", description = "Remove a category from an existing item. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed category from item"),
            @ApiResponse(responseCode = "400", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @DeleteMapping("/{itemId}/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDto removeCategory(@PathVariable Long itemId, @RequestParam String category) {

        return itemService.removeCategory(itemId, category);
    }
}