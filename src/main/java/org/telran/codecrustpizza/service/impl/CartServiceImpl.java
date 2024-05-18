package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.cart.CartResponseDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.entity.Cart;
import org.telran.codecrustpizza.entity.CartItem;
import org.telran.codecrustpizza.entity.Item;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.CartItemMapper;
import org.telran.codecrustpizza.repository.CartItemRepository;
import org.telran.codecrustpizza.repository.CartRepository;
import org.telran.codecrustpizza.repository.ItemRepository;
import org.telran.codecrustpizza.repository.UserRepository;
import org.telran.codecrustpizza.service.CartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("user", userId)));

        Optional<Cart> cart = cartRepository.findByUser(user);
        if (cart.isPresent()) return cart.get();

        Cart newCart = Cart.builder().user(user).build();
        user.setCart(newCart);
        cartRepository.save(newCart);

        return newCart;
    }

    @Override
    public CartResponseDto addItemToCart(Long itemId, Long userId) {
        Cart cart = getCart(userId);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", itemId)));
        Optional<CartItem> existingCartItem = cartItemRepository.findByCart_IdAndItem_Id(cart.getId(), itemId);
        CartItem cartItem;

        if (existingCartItem.isEmpty()) {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .item(item)
                    .quantity(1)
                    .build();
        } else {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        cartItem.setPrice(BigDecimal.valueOf(item.getPrice().doubleValue() * cartItem.getQuantity()));

        cartItemRepository.save(cartItem);

        return null;
    }

    @Override
    public CartResponseDto removeItemFromCart(Long itemId, Long userId) {
        return null;
    }

    @Override
    public List<ItemResponseDto> getCartItems(Long userId) {
        return List.of();
    }

    @Override
    public CartResponseDto clearCart(Long userId) {
        return null;
    }
}
