package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.cart.CartItemResponseDto;
import org.telran.codecrustpizza.dto.cart.CartResponseDto;
import org.telran.codecrustpizza.service.CartService;
import org.telran.codecrustpizza.service.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart Controller", description = "Operations related to cart management")
public class CartController {

    private final UserService userService;
    private final CartService cartService;

    @Operation(summary = "Get current user's cart items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart items",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDto.class))}),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<CartItemResponseDto> getCurrentUserCartItems() {
        Long userId = userService.getCurrentUserId();

        return cartService.getCartItems(userId);
    }

    @Operation(summary = "Add an item to the current user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added item to cart",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Item you are trying to add not found")
    })
    @PutMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto addItem(@RequestParam Long itemId) {
        Long userId = userService.getCurrentUserId();

        return cartService.addItemToCart(itemId, userId);
    }

    @Operation(summary = "Remove an item from the current user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed item from cart",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Item you are trying to remove not found in cart")
    })
    @PutMapping("/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto removeItem(@RequestParam Long itemId) {
        Long userId = userService.getCurrentUserId();

        return cartService.removeItemFromCart(itemId, userId);
    }

    @Operation(summary = "Clear the current user's cart")
    @ApiResponse(responseCode = "200", description = "Successfully cleared cart",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartResponseDto.class))})
    @PutMapping("/clear")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto clearCurrentUserCart() {
        Long userId = userService.getCurrentUserId();

        return cartService.clearCart(userId);
    }
}