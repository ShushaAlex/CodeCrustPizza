package org.telran.codecrustpizza.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.IngredientMapper;
import org.telran.codecrustpizza.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.telran.codecrustpizza.testData.IngredientServiceTestData.INGREDIENT_1;
import static org.telran.codecrustpizza.testData.IngredientServiceTestData.INGREDIENT_CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.IngredientServiceTestData.INGREDIENT_RESPONSE_DTO_1;
import static org.telran.codecrustpizza.testData.IngredientServiceTestData.TITLE_1;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = IngredientServiceImpl.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientMapper ingredientMapper;
    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Test
    void getAllTest() {
        // Prepare data
        List<Ingredient> ingredients = List.of(new Ingredient(), new Ingredient(), new Ingredient());
        IngredientResponseDto responseDto = INGREDIENT_RESPONSE_DTO_1;

        when(ingredientRepository.findAll()).thenReturn(ingredients);
        when(ingredientMapper.toDto(any(Ingredient.class))).thenReturn(responseDto);
        // Execute
        List<IngredientResponseDto> result = ingredientService.getAll();
        // Validate
        assertEquals(ingredients.size(), result.size());
        ingredients.forEach(ingredient -> verify(ingredientMapper, times(3)).toDto(ingredient));
    }

    @Test
    void getIngredientDtoByIdSuccessTest() {
        // Prepare data
        Long id = 1L;
        Ingredient ingredient = INGREDIENT_1;
        IngredientResponseDto responseDto = INGREDIENT_RESPONSE_DTO_1;

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.toDto(ingredient)).thenReturn(responseDto);
        // Execute
        IngredientResponseDto result = ingredientService.getIngredientDtoById(id);
        // Validate
        assertEquals(responseDto.title(), result.title());
        verify(ingredientRepository, times(1)).findById(id);
        verify(ingredientMapper, times(1)).toDto(ingredient);
    }

    @Test
    void getIngredientDtoByIdThrowsEntityExceptionTest() {
        // Prepare data
        Long id = 1L;

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> ingredientService.getIngredientDtoById(id));
        verify(ingredientRepository, times(1)).findById(id);
    }

    @Test
    void saveIngredientSuccessTest() {
        // Prepare data
        String title = TITLE_1;
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;
        Ingredient ingredient = new Ingredient();
        IngredientResponseDto responseDto = INGREDIENT_RESPONSE_DTO_1;

        when(ingredientRepository.findByTitle(title)).thenReturn(Optional.empty());
        when(ingredientMapper.toIngredient(createDto)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toDto(ingredient)).thenReturn(responseDto);
        // Execute
        IngredientResponseDto result = ingredientService.saveIngredient(createDto);
        // Validate
        assertEquals(responseDto.title(), result.title());
        verify(ingredientRepository, times(1)).findByTitle(title);
        verify(ingredientRepository, times(1)).save(ingredient);
        verify(ingredientMapper, times(1)).toIngredient(createDto);
        verify(ingredientMapper, times(1)).toDto(ingredient);
    }

    @Test
    void saveIngredientThrowsEntityExceptionTest() {
        // Prepare data
        String title = TITLE_1;
        Ingredient ingredient = new Ingredient();
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;

        when(ingredientRepository.findByTitle(title)).thenReturn(Optional.of(ingredient));
        // Validate
        assertThrows(EntityException.class, () -> ingredientService.saveIngredient(createDto));
        verify(ingredientRepository, times(1)).findByTitle(title);
    }

    @Test
    void deleteIngredientSuccessTest() {
        // Prepare data
        Long id = 1L;
        Ingredient ingredient = INGREDIENT_1;
        IngredientResponseDto responseDto = INGREDIENT_RESPONSE_DTO_1;

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.toDto(ingredient)).thenReturn(responseDto);
        // Execute
        IngredientResponseDto result = ingredientService.deleteIngredient(id);
        // Validate
        assertEquals(responseDto.id(), result.id());
        verify(ingredientMapper, times(1)).toDto(ingredient);
        verify(ingredientRepository, times(1)).delete(ingredient);
    }

    @Test
    void deleteIngredientThrowsEntityExceptionTest() {
        // Prepare data
        Long id = 1L;

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> ingredientService.deleteIngredient(id));
        verify(ingredientRepository, times(1)).findById(id);
    }

    @Test
    void updateIngredientSuccessTest() {
        // Prepare data
        Long id = 1L;
        Ingredient ingredient = INGREDIENT_1;
        IngredientResponseDto responseDto = INGREDIENT_RESPONSE_DTO_1;
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toDto(ingredient)).thenReturn(responseDto);
        // Execute
        IngredientResponseDto result = ingredientService.updateIngredient(id, createDto);
        // Validate
        assertEquals(responseDto.id(), result.id());
        verify(ingredientRepository, times(1)).findById(id);
        verify(ingredientRepository, times(1)).save(ingredient);
        verify(ingredientMapper, times(1)).toDto(ingredient);
    }

    @Test
    void updateIngredientThrowsEntityExceptionTest() {
        // Prepare data
        Long id = 1L;
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());
        // Validate
        assertThrows(EntityException.class, () -> ingredientService.updateIngredient(id, createDto));
        verify(ingredientRepository, times(1)).findById(id);
    }
}