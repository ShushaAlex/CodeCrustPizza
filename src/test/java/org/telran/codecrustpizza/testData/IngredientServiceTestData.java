package org.telran.codecrustpizza.testData;

import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;

import java.math.BigDecimal;

public class IngredientServiceTestData {

    public static String TITLE_1 = "TITLE_1";
    public static String TITLE_2 = "TITLE_2";
    public static BigDecimal PRICE_1 = new BigDecimal("10.47");
    public static BigDecimal PRICE_2 = new BigDecimal("99.99");
    public static int CALORIES_1 = 145;
    public static int CALORIES_2 = 26;

    public static Ingredient INGREDIENT_1 = Ingredient.builder()
            .id(1L)
            .title(TITLE_1)
            .price(PRICE_1)
            .calories(CALORIES_1)
            .build();

    public static Ingredient INGREDIENT_2 = Ingredient.builder()
            .id(2L)
            .title(TITLE_2)
            .price(PRICE_2)
            .calories(CALORIES_2)
            .build();

    public static IngredientCreateRequestDto INGREDIENT_CREATE_DTO_1 = new IngredientCreateRequestDto(
            TITLE_1,
            PRICE_1,
            CALORIES_1
    );

    public static IngredientCreateRequestDto INGREDIENT_CREATE_DTO_2 = new IngredientCreateRequestDto(
            TITLE_2,
            PRICE_2,
            CALORIES_2
    );

    public static IngredientResponseDto INGREDIENT_RESPONSE_DTO_1 = new IngredientResponseDto(
            1L,
            TITLE_1,
            PRICE_1,
            CALORIES_1
    );

    public static IngredientResponseDto INGREDIENT_RESPONSE_DTO_2 = new IngredientResponseDto(
            2L,
            TITLE_2,
            PRICE_2,
            CALORIES_2
    );
}