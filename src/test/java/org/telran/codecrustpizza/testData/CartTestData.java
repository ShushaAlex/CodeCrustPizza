package org.telran.codecrustpizza.testData;

import org.telran.codecrustpizza.entity.Cart;
import org.telran.codecrustpizza.entity.Item;
import org.telran.codecrustpizza.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartTestData {

    public static String TITLE_1 = "TITLE_1";
    public static String TITLE_2 = "TITLE_2";
    public static String DESCRIPTION_1 = "DESCRIPTION_1";
    public static String DESCRIPTION_2 = "DESCRIPTION_2";
    public static BigDecimal PRICE_1 = new BigDecimal("10.50");
    public static int QUANTITY_1 = 2;
    public static LocalDateTime CREATION_DATE = LocalDateTime.now();

    public static User USER_1 = User.builder()
            .id(1L)
            .name("NAME_1")
            .email("EMAIL_1")
            .password("PASSWORD_1")
            .creationDate(CREATION_DATE)
            .build();


    public static Item ITEM_1 = Item.builder()
            .id(1L)
            .title(TITLE_1)
            .description(DESCRIPTION_1)
            .price(PRICE_1)
            .build();

    public static Item ITEM_2 = Item.builder()
            .id(2L)
            .title(TITLE_2)
            .description(DESCRIPTION_2)
            .price(PRICE_1)
            .build();

    public static Cart CART_1 = Cart.builder()
            .id(1L)
            .user(USER_1)
            .build();
}