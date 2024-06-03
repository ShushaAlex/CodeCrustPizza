package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.cart.CartItemResponseDto;
import org.telran.codecrustpizza.dto.cart.CartResponseDto;
import org.telran.codecrustpizza.entity.Cart;

import java.util.List;

/**
 * Interface for cart-related operations.
 */
public interface CartService {

    /**
     * Retrieves the cart associated with the specified user.
     *
     * @param userId the ID of the user whose cart is to be retrieved.
     * @return the cart associated with the specified user.
     */
    Cart getCart(Long userId);

    /**
     * Adds an item to the cart of the specified user.
     *
     * @param itemId the ID of the item to be added to the cart.
     * @param userId the ID of the user whose cart is to be updated.
     * @return a response DTO containing information about the updated cart.
     */
    CartResponseDto addItemToCart(Long itemId, Long userId);

    /**
     * Removes an item from the cart of the specified user.
     *
     * @param itemId the ID of the item to be removed from the cart.
     * @param userId the ID of the user whose cart is to be updated.
     * @return a response DTO containing information about the updated cart.
     */
    CartResponseDto removeItemFromCart(Long itemId, Long userId);

    /**
     * Retrieves the items in the cart of the specified user.
     *
     * @param userId the ID of the user whose cart items are to be retrieved.
     * @return a list of response DTOs containing information about the items in the cart.
     */
    List<CartItemResponseDto> getCartItems(Long userId);

    /**
     * Clears the cart of the specified user.
     *
     * @param userId the ID of the user whose cart is to be cleared.
     * @return a response DTO containing information about the updated cart.
     */
    CartResponseDto clearCart(Long userId);
}