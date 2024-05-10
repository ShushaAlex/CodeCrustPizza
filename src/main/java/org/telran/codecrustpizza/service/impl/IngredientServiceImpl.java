package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.IngredientMapper;
import org.telran.codecrustpizza.repository.IngredientRepository;
import org.telran.codecrustpizza.service.IngredientService;

import java.util.List;
import java.util.Optional;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    @Override
    public IngredientResponseDto getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("ingredient", id)));

        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public IngredientResponseDto saveIngredient(IngredientCreateRequestDto createDto) {
        Optional<Ingredient> existingIngredient = ingredientRepository.findByTitle(createDto.title());
        if (existingIngredient.isPresent()) throw new EntityException(ENTITY_EXIST.getCustomMessage("ingredient"));

        Ingredient ingredient = ingredientMapper.toIngredient(createDto);
        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public IngredientResponseDto deleteIngredient(Long id) { //TODO подумать над каскадом изменений после удаления ингредиента
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("ingredient", id)));

        ingredientRepository.delete(ingredient);

        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public IngredientResponseDto updateIngredient(Long id, IngredientCreateRequestDto createDto) {
        return null;
    }
}
