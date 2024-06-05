package org.telran.codecrustpizza.testData;

import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.entity.enums.MenuCategory;

import java.math.BigDecimal;

public class ItemTestData {

    public static String TITLE_1 = "some title";
    public static String DESCR_1 = "some description";
    public static BigDecimal PRICE_1 = BigDecimal.valueOf(7.99);

    public static ItemCreateRequestDto ITEM_CREATE_DTO = new ItemCreateRequestDto(
            TITLE_1,
            DESCR_1,
            MenuCategory.SALADS,
            PRICE_1
    );
}
