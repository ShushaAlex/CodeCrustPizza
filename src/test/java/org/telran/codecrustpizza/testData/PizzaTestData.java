package org.telran.codecrustpizza.testData;

import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaIngredientResponseDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;
import org.telran.codecrustpizza.entity.Pizza;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.entity.enums.Dough;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PizzaTestData {

    public static String TITLE_1 = "TITLE_1";
    public static String TITLE_2 = "TITLE_2";
    public static String DESCRIPTION_1 = "DESCRIPTION_1";
    public static String DESCRIPTION_2 = "DESCRIPTION_2";
    public static BigDecimal PRICE_1 = new BigDecimal("10.50");
    public static BigDecimal PRICE_2 = new BigDecimal("100.00");
    public static int CALORIES_1 = 100;
    public static int CALORIES_2 = 15;
    public static int SIZE_1 = 20;
    public static int SIZE_2 = 30;
    public static int QUANTITY_1 = 50;
    public static int QUANTITY_2 = 100;

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

    public static PizzaPattern PIZZA_PATTERN_1 = PizzaPattern.builder()
            .id(1L)
            .title(TITLE_1)
            .description(DESCRIPTION_1)
            .size(SIZE_1)
            .dough(Dough.THICK)
            .build();

    public static PizzaPatternIngredient PATTERN_INGREDIENT_1 = PizzaPatternIngredient.builder()
            .id(1L)
            .pizzaPattern(PIZZA_PATTERN_1)
            .ingredient(INGREDIENT_1)
            .quantity(QUANTITY_1)
            .build();

    public static PizzaPatternIngredient PATTERN_INGREDIENT_2 = PizzaPatternIngredient.builder()
            .id(2L)
            .pizzaPattern(PIZZA_PATTERN_1)
            .ingredient(INGREDIENT_2)
            .quantity(QUANTITY_2)
            .build();

    public static Set<PizzaPatternIngredient> PIZZA_PATTERN_INGREDIENTS = new HashSet<>(Set.of(PATTERN_INGREDIENT_2, PATTERN_INGREDIENT_1));

    public static Pizza PIZZA_1 = Pizza.builder()
            .id(1L)
            .title(TITLE_2)
            .description(DESCRIPTION_2)
            .pizzaPattern(PIZZA_PATTERN_1)
            .size(SIZE_2)
            .dough(Dough.THICK)
            .build();

    public static PizzaCreateRequestDto CREATE_DTO_1 = new PizzaCreateRequestDto(
            1L,
            SIZE_2,
            TITLE_2
    );

    public static PizzaResponseDto RESPONSE_DTO_1 = new PizzaResponseDto(
            1L,
            TITLE_2,
            Dough.THICK,
            DESCRIPTION_2,
            SIZE_2,
            new HashSet<PizzaIngredientResponseDto>(),
            CALORIES_2,
            PRICE_2
    );

    public static PizzaIngredient PIZZA_INGREDIENT_1 = PizzaIngredient.builder()
            .id(1L)
            .pizza(PIZZA_1)
            .ingredient(INGREDIENT_1)
            .quantity(QUANTITY_1)
            .build();

    public static PizzaIngredient PIZZA_INGREDIENT_2 = PizzaIngredient.builder()
            .id(2L)
            .pizza(PIZZA_1)
            .ingredient(INGREDIENT_2)
            .quantity(QUANTITY_2)
            .build();
}