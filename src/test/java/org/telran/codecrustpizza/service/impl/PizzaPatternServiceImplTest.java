package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.entity.enums.Dough;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.PizzaPatternMapper;
import org.telran.codecrustpizza.repository.PizzaPatternRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.PATTERN_INGREDIENT_1;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.PATTERN_INGREDIENT_2;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.PIZZA_PATTERN_1;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.RESPONSE_DTO_1;
import static org.telran.codecrustpizza.testData.PizzaPatternTestData.TITLE_1;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = PizzaPatternServiceImpl.class)
class PizzaPatternServiceImplTest {

    @Mock
    private PizzaPatternRepository pizzaPatternRepository;
    @Mock
    private PizzaPatternMapper pizzaPatternMapper;
    @InjectMocks
    private PizzaPatternServiceImpl pizzaPatternService;

    @Test
    void findAllSuccessTest() {
        // Prepare data
        List<PizzaPattern> pizzaPatterns = List.of(new PizzaPattern(), new PizzaPattern(), new PizzaPattern());
        PizzaPatternResponseDto responseDto = RESPONSE_DTO_1;

        when(pizzaPatternRepository.findAll()).thenReturn(pizzaPatterns);
        when(pizzaPatternMapper.toDto(any(PizzaPattern.class))).thenReturn(responseDto);
        // Execute
        List<PizzaPatternResponseDto> result = pizzaPatternService.findAll();
        // Validate
        assertEquals(pizzaPatterns.size(), result.size());
        pizzaPatterns.forEach(pizzaPattern -> verify(pizzaPatternMapper, times(3)).toDto(pizzaPattern));
    }

    @Test
    void createPizzaSuccessTest() {
        // Prepare data
        PizzaPatternCreateDto createDto = CREATE_DTO_1;
        PizzaPattern pizzaPattern = PIZZA_PATTERN_1;
        PizzaPatternResponseDto responseDto = RESPONSE_DTO_1;
        String title = TITLE_1;
        Dough dough = Dough.THICK;

        when(pizzaPatternRepository.findByTitleAndDough(title, dough)).thenReturn(Optional.empty());
        when(pizzaPatternMapper.toPizzaPattern(createDto)).thenReturn(pizzaPattern);
        when(pizzaPatternRepository.save(pizzaPattern)).thenReturn(pizzaPattern);
        when(pizzaPatternMapper.toDto(pizzaPattern)).thenReturn(responseDto);
        // Execute
        PizzaPatternResponseDto result = pizzaPatternService.createPizza(CREATE_DTO_1);
        // Validate
        assertEquals(responseDto.id(), result.id());
        verify(pizzaPatternRepository, times(1)).findByTitleAndDough(title, dough);
        verify(pizzaPatternRepository, times(1)).save(pizzaPattern);
        verify(pizzaPatternMapper, times(1)).toPizzaPattern(createDto);
        verify(pizzaPatternMapper, times(1)).toDto(pizzaPattern);
    }

    @Test
    void createPizzaThrowsEntityException() {
        // Prepare data
        PizzaPatternCreateDto createDto = CREATE_DTO_1;
        String title = TITLE_1;
        Dough dough = Dough.THICK;

        when(pizzaPatternRepository.findByTitleAndDough(title, dough)).thenReturn(Optional.of(new PizzaPattern()));
        //Validate
        assertThrows(EntityException.class, () -> pizzaPatternService.createPizza(createDto));
        verify(pizzaPatternRepository, times(1)).findByTitleAndDough(title, dough);
    }

    @Test
    void getPizzaByIdSuccessTest() {
        // Prepare data
        Long id = 1L;
        PizzaPattern pizzaPattern = PIZZA_PATTERN_1;
        PizzaPatternResponseDto responseDto = RESPONSE_DTO_1;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.of(pizzaPattern));
        when(pizzaPatternMapper.toDto(pizzaPattern)).thenReturn(responseDto);
        // Execute
        PizzaPatternResponseDto result = pizzaPatternService.getPizzaById(id);
        // Validate
        assertEquals(responseDto.id(), result.id());
        verify(pizzaPatternRepository, times(1)).findById(id);
        verify(pizzaPatternMapper, times(1)).toDto(pizzaPattern);
    }

    @Test
    void getByIdThrowsEntityException() {
        // Prepare data
        Long id = 1L;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> pizzaPatternService.getPizzaById(id));
        verify(pizzaPatternRepository, times(1)).findById(id);
    }

    @Test
    void updatePizzaSuccessTest() {
        // Prepare data
        Long id = 1L;
        PizzaPatternCreateDto createDto = CREATE_DTO_1;
        PizzaPattern pizzaPattern = PIZZA_PATTERN_1;
        PizzaPatternResponseDto responseDto = RESPONSE_DTO_1;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.of(pizzaPattern));
        when(pizzaPatternRepository.save(pizzaPattern)).thenReturn(pizzaPattern);
        when(pizzaPatternMapper.toDto(pizzaPattern)).thenReturn(responseDto);
        //Execute
        PizzaPatternResponseDto result = pizzaPatternService.updatePizza(id, createDto);
        // Validate
        assertEquals(responseDto.id(), result.id());
        verify(pizzaPatternRepository, times(1)).findById(id);
        verify(pizzaPatternRepository, times(1)).save(pizzaPattern);
        verify(pizzaPatternMapper, times(1)).toDto(pizzaPattern);
    }

    @Test
    void updatePizzaThrowsEntityException() {
        // Prepare data
        Long id = 1L;
        PizzaPatternCreateDto createDto = CREATE_DTO_1;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> pizzaPatternService.updatePizza(id, createDto));
        verify(pizzaPatternRepository, times(1)).findById(id);
    }

    @Test
    void deletePizzaSuccessTest() {
        // Prepare data
        Long id = 1L;
        PizzaPattern pizzaPattern = PIZZA_PATTERN_1;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.of(pizzaPattern));
        // Execute
        boolean result = pizzaPatternService.deletePizza(id);
        // Validate
        assertTrue(result);
        verify(pizzaPatternRepository, times(1)).findById(id);
    }

    @Test
    void deletePizzaThrowsEntityException() {
        // Prepare Data
        Long id = 1L;

        when(pizzaPatternRepository.findById(id)).thenReturn(Optional.empty());
        //Validate
        assertThrows(EntityException.class, () -> pizzaPatternService.deletePizza(id));
        verify(pizzaPatternRepository, times(1)).findById(id);
    }

    @Test
    void calculateTotalCaloriesSuccessTest() {
        // Prepare data
        Set<PizzaPatternIngredient> ingredients = new HashSet<>(Set.of(PATTERN_INGREDIENT_1, PATTERN_INGREDIENT_2));
        int expected = 65;
        // Execute
        int result = pizzaPatternService.calculateTotalCalories(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalCaloriesIngredientsIsEmptyTest() {
        // Prepare data
        Set<PizzaPatternIngredient> ingredients = new HashSet<>();
        int expected = 0;
        //Execute
        int result = pizzaPatternService.calculateTotalCalories(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalCaloriesThrowsIllArgException() {
        // Validate
        assertThrows(IllegalArgumentException.class, () -> pizzaPatternService.calculateTotalCalories(null));
    }

    @Test
    void calculateTotalPriceSuccessTest() {
        // Prepare data
        Set<PizzaPatternIngredient> ingredients = new HashSet<>(Set.of(PATTERN_INGREDIENT_1, PATTERN_INGREDIENT_2));
        BigDecimal expected = BigDecimal.valueOf(105.25);
        // Exec
        BigDecimal result = pizzaPatternService.calculateTotalPrice(ingredients);
        // Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalPriceIngredientsIsEmptyTest() {
        // Prepare data
        Set<PizzaPatternIngredient> ingredients = new HashSet<>();
        BigDecimal expected = BigDecimal.ZERO;
        //Exec
        BigDecimal result = pizzaPatternService.calculateTotalPrice(ingredients);
        //Validate
        assertEquals(expected, result);
    }

    @Test
    void calculateTotalPriceThrowsIllArgException() {
        assertThrows(IllegalArgumentException.class, () -> pizzaPatternService.calculateTotalPrice(null));
    }
}