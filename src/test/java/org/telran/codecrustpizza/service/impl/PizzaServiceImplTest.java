package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.entity.Pizza;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.PizzaMapper;
import org.telran.codecrustpizza.repository.PizzaPatternRepository;
import org.telran.codecrustpizza.repository.PizzaRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telran.codecrustpizza.testData.PizzaTestData.CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.PIZZA_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.PIZZA_INGREDIENT_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.PIZZA_INGREDIENT_2;
import static org.telran.codecrustpizza.testData.PizzaTestData.PIZZA_PATTERN_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.PIZZA_PATTERN_INGREDIENTS;
import static org.telran.codecrustpizza.testData.PizzaTestData.RESPONSE_DTO_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.TITLE_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.TITLE_2;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = PizzaServiceImpl.class)
class PizzaServiceImplTest {

    @Mock
    private PizzaPatternRepository pizzaPatternRepository;
    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private PizzaMapper pizzaMapper;
    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @Test
    void findAllSuccessTest() {
        // Prepare data
        List<Pizza> pizzas = List.of(new Pizza(), new Pizza(), new Pizza());
        PizzaResponseDto responseDto = RESPONSE_DTO_1;

        when(pizzaRepository.findAll()).thenReturn(pizzas);
        when(pizzaMapper.toDto(any(Pizza.class))).thenReturn(responseDto);
        // Execute
        List<PizzaResponseDto> result = pizzaService.findAll();
        // Validate
        assertEquals(pizzas.size(), result.size());
        pizzas.forEach(pizza -> verify(pizzaMapper, times(3)).toDto(pizza));
    }

    @Test
    void createPizzaSuccessTest() {
        // Prepare data
        PizzaCreateRequestDto createDto = CREATE_DTO_1;
        PizzaResponseDto responseDto = RESPONSE_DTO_1;
        PizzaPattern pizzaPattern = PIZZA_PATTERN_1;
        String title = TITLE_2;
        Pizza pizza = PIZZA_1;
        Long pizzaPatternId = 1L;

        when(pizzaRepository.findByTitle(title)).thenReturn(Optional.empty());
        when(pizzaPatternRepository.findById(pizzaPatternId)).thenReturn(Optional.of(pizzaPattern));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);
        when(pizzaMapper.toDto(pizza)).thenReturn(responseDto);
        //Exec
        PizzaResponseDto result = pizzaService.createPizza(createDto);
        //Validate
        assertEquals(responseDto.id(), result.id());
        verify(pizzaRepository, times(1)).findByTitle(title);
        verify(pizzaRepository, times(1)).save(any(Pizza.class));
        verify(pizzaPatternRepository, times(1)).findById(pizzaPatternId);
        verify(pizzaMapper, times(1)).toDto(pizza);
    }

    @Test
    void createPizzaThrowsEntityExceptionPizzaExistTest() {
        // Prepare data
        PizzaCreateRequestDto createDto = CREATE_DTO_1;
        String title = TITLE_2;

        when(pizzaRepository.findByTitle(title)).thenReturn(Optional.of(new Pizza()));
        //Validate
        assertThrows(EntityException.class, () -> pizzaService.createPizza(createDto));
    }

    @Test
    void createPizzaThrowsEntityExceptionPizzaPatternNotExistTest() {
        // Prepare data
        PizzaCreateRequestDto createDto = CREATE_DTO_1;
        String title = TITLE_2;
        Long pizzaPatternId = 1L;

        when(pizzaRepository.findByTitle(title)).thenReturn(Optional.empty());
        when(pizzaPatternRepository.findById(pizzaPatternId)).thenReturn(Optional.empty());
        //Validate
        assertThrows(EntityException.class, () -> pizzaService.createPizza(createDto));
    }

    @Test
    void getPizzaByIdSuccessTest() {
        //Prepare data
        Long id = 1L;
        Pizza pizza = PIZZA_1;
        PizzaResponseDto responseDto = RESPONSE_DTO_1;

        when(pizzaRepository.findById(id)).thenReturn(Optional.of(pizza));
        when(pizzaMapper.toDto(pizza)).thenReturn(responseDto);
        // Execute
        PizzaResponseDto result = pizzaService.getPizzaDtoById(id);
        //Validate
        assertEquals(responseDto.id(), result.id());
        verify(pizzaRepository, times(1)).findById(id);
        verify(pizzaMapper, times(1)).toDto(pizza);
    }

    @Test
    void getPizzaByIdThrowsEntityExceptionTest() {
        //Prepare data
        Long id = 1L;

        when(pizzaRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> pizzaService.getPizzaDtoById(id));
    }

    @Test
    void calculateTotalCaloriesSuccessTest() {
        //Prepare data
        Set<PizzaIngredient> ingredients = new HashSet<>(Set.of(PIZZA_INGREDIENT_1, PIZZA_INGREDIENT_2));
        int expected = 65;

        //Exec
        int result = pizzaService.calculateTotalCalories(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalCaloriesIngredientsIsEmptyTest() {
        // Prepare data
        Set<PizzaIngredient> ingredients = new HashSet<>();
        int expected = 0;
        //Execute
        int result = pizzaService.calculateTotalCalories(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalCaloriesThrowsIllArgException() {
        // Validate
        assertThrows(IllegalArgumentException.class, () -> pizzaService.calculateTotalCalories(null));
    }

    @Test
    void calculateTotalPriceSuccessTest() {
        // Prepare data
        Set<PizzaIngredient> ingredients = new HashSet<>(Set.of(PIZZA_INGREDIENT_1, PIZZA_INGREDIENT_2));
        BigDecimal expected = BigDecimal.valueOf(105.25);
        // Exec
        BigDecimal result = pizzaService.calculateTotalPrice(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalPriceIngredientsIsEmptyTest() {
        // Prepare data
        Set<PizzaIngredient> ingredients = new HashSet<>();
        BigDecimal expected = BigDecimal.ZERO;
        //Exec
        BigDecimal result = pizzaService.calculateTotalPrice(ingredients);
        //Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalPriceThrowsIllArgException() {
        assertThrows(IllegalArgumentException.class, () -> pizzaService.calculateTotalPrice(null));
    }

    @Test
    void calculateIngredientQuantity() {
        // Prepare data
        Pizza pizza = PIZZA_1.toBuilder().build();
        PizzaPattern pattern = PIZZA_PATTERN_1.toBuilder().build();
        pattern.setPatternIngredients(PIZZA_PATTERN_INGREDIENTS);
        pizza.setPizzaPattern(pattern);
        int expectedSetSize = 2;
        int expectedQuantity1 = 78;
        int expectedQuantity2 = 156;
        //Execute
        Set<PizzaIngredient> result = pizzaService.calculateIngredientQuantity(pizza);
        List<PizzaIngredient> ingredientList = result.stream().toList();
        PizzaIngredient pizzaIngredient1 = ingredientList.stream()
                .filter(i -> i.getIngredient().getTitle().equals(TITLE_1))
                .findFirst().orElse(new PizzaIngredient());
        PizzaIngredient pizzaIngredient2 = ingredientList.stream()
                .filter(i -> i.getIngredient().getTitle().equals(TITLE_2))
                .findFirst().orElse(new PizzaIngredient());
        //Validate
        assertEquals(expectedSetSize, result.size());
        assertEquals(expectedQuantity1, pizzaIngredient1.getQuantity());
        assertEquals(expectedQuantity2, pizzaIngredient2.getQuantity());
    }

    @Test
    void calculateSizeDifSuccessTest() {
        //Prepare data
        int patternSize = 10;
        int pizzaSize = 20;
        double expected = 0.75;
        //Execute
        double result = pizzaService.calculateSizeDif(patternSize, pizzaSize);
        //Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateSizeDifSuccessSameSizeTest() {
        //Prepare data
        int size = 10;
        double expected = 0.0;
        //Execute
        double result = pizzaService.calculateSizeDif(size, size);
        //Validate
        assertEquals(expected, result);
    }
}