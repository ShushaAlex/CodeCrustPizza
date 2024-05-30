package org.telran.codecrustpizza.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.codecrustpizza.entity.Cart;
import org.telran.codecrustpizza.entity.CartItem;
import org.telran.codecrustpizza.entity.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.telran.codecrustpizza.testData.CartTestData.CART_1;
import static org.telran.codecrustpizza.testData.CartTestData.CREATION_DATE;
import static org.telran.codecrustpizza.testData.CartTestData.ITEM_1;
import static org.telran.codecrustpizza.testData.CartTestData.ITEM_2;
import static org.telran.codecrustpizza.testData.CartTestData.PRICE_1;
import static org.telran.codecrustpizza.testData.CartTestData.QUANTITY_1;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;

    Item item1;
    Item item2;
    Cart cart;
    CartItem cartItem1;
    CartItem cartItem2;

    @BeforeEach
    public void setUp() {
        item1 = ITEM_1;
        item2 = ITEM_2;
        cart = CART_1;
        BigDecimal price1 = PRICE_1;
        LocalDateTime creationDate = CREATION_DATE;
        int quantity = QUANTITY_1;

        itemRepository.save(item1);
        itemRepository.save(item2);

        cartRepository.save(cart);

//        cartItem1 = CartItem.builder().id(1L).cart(cart).item(item1).price(price1).quantity(quantity).creationDate(creationDate).build();
//        cartItem2 = CartItem.builder().id(2L).cart(cart).item(item2).price(price1).quantity(quantity).creationDate(creationDate).build();
//
//        cartItemRepository.save(cartItem1);
//        cartItemRepository.save(cartItem2);
    }

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
    }

    @Test
    void getCartItemByCart_Id() {

        List<CartItem> cartItems = cartItemRepository.getCartItemByCart_Id(cart.getId());

        assertEquals(2, cartItems.size());
    }

    @Test
    void findByCart_IdAndItem_Id() {
    }
}