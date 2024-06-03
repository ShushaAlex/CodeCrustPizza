package org.telran.codecrustpizza.controller;

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
public class CartController {

    private final UserService userService;
    private final CartService cartService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<CartItemResponseDto> getCurrentUserCartItems() {
        Long userId = userService.getCurrentUserId();

        return cartService.getCartItems(userId);
    }

    @PutMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto addItem(@RequestParam Long itemId) {
        Long userId = userService.getCurrentUserId();

        return cartService.addItemToCart(itemId, userId);
    }

    @PutMapping("/remove")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto removeItem(@RequestParam Long itemId) {
        Long userId = userService.getCurrentUserId();

        return cartService.removeItemFromCart(itemId, userId);
    }

    @PutMapping("/clear")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CartResponseDto clearCurrentUserCart() {
        Long userId = userService.getCurrentUserId();

        return cartService.clearCart(userId);
    }
}