package org.telran.codecrustpizza.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.pizza.PizzaPatternIngredient.PizzaPatternIngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.Ingredient;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.entity.enums.Dough;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.PizzaPatternMapper;
import org.telran.codecrustpizza.repository.PizzaPatternRepository;
import org.telran.codecrustpizza.service.IngredientService;
import org.telran.codecrustpizza.service.PizzaService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
@RequiredArgsConstructor
public class PizzaPatternServiceImpl implements PizzaService<PizzaPatternResponseDto, PizzaPatternCreateDto, PizzaPatternIngredient, PizzaPattern> {

    private final PizzaPatternRepository pizzaPatternRepository;
    private final PizzaPatternMapper pizzaPatternMapper;
    private final IngredientService ingredientService;

    @Override
    @Transactional
    public List<PizzaPatternResponseDto> findAll() {
        return pizzaPatternRepository.findAll()
                .stream()
                .map(pizzaPatternMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PizzaPatternResponseDto createPizza(PizzaPatternCreateDto pizzaCreateDto) {

        Optional<PizzaPattern> existingPizza = pizzaPatternRepository.findByTitleAndDough(pizzaCreateDto.title(), Dough.valueOf(pizzaCreateDto.dough()));
        if (existingPizza.isPresent()) throw new EntityException(ENTITY_EXIST.getCustomMessage("PizzaPattern"));

        Set<PizzaPatternIngredient> patternIngredients = getPizzaPatternIngredients(pizzaCreateDto.patternIngredients());

        PizzaPattern pizzaPattern = pizzaPatternMapper.toPizzaPattern(pizzaCreateDto);

        for (PizzaPatternIngredient patternIngredient : patternIngredients) {
            pizzaPattern.addPizzaPatternIngredient(patternIngredient);
        }
        pizzaPattern.setPatternIngredients(patternIngredients);
        pizzaPattern.setCalories(calculateTotalCalories(patternIngredients));
        pizzaPattern.setPrice(calculateTotalPrice(patternIngredients));

        pizzaPattern = pizzaPatternRepository.save(pizzaPattern);

        return pizzaPatternMapper.toDto(pizzaPattern);
    }

    @Override
    @Transactional
    public PizzaPatternResponseDto getPizzaDtoById(Long id) {

        return pizzaPatternMapper.toDto(getPizzaById(id));
    }

    @Override
    @Transactional
    public PizzaPattern getPizzaById(Long id) {

        return pizzaPatternRepository.findById(id)
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", id)));
    }

    @Override
    @Transactional
    public PizzaPatternResponseDto updatePizza(Long id, PizzaPatternCreateDto pizzaCreateDto) {
        PizzaPattern pizzaPattern = getPizzaById(id);

        Set<PizzaPatternIngredient> patternIngredients = getPizzaPatternIngredients(pizzaCreateDto.patternIngredients());

        pizzaPattern.setTitle(pizzaCreateDto.title());
        pizzaPattern.setDescription(pizzaCreateDto.description());
        pizzaPattern.setSize(pizzaCreateDto.size());
        pizzaPattern.setDough(Dough.valueOf(pizzaCreateDto.dough()));
        pizzaPattern.setPatternIngredients(patternIngredients);

        for (PizzaPatternIngredient patternIngredient : patternIngredients) {
            pizzaPattern.addPizzaPatternIngredient(patternIngredient);
        }

        pizzaPattern.setCalories(calculateTotalCalories(patternIngredients));
        pizzaPattern.setPrice(calculateTotalPrice(patternIngredients));

        pizzaPattern = pizzaPatternRepository.save(pizzaPattern);

        return pizzaPatternMapper.toDto(pizzaPattern);
    }

    @Override
    @Transactional
    public boolean deletePizza(Long id) { // TODO подумать над логикой удаления
        PizzaPattern pizzaPattern = pizzaPatternRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", id)));

        pizzaPatternRepository.delete(pizzaPattern);
        return true;
    }

    @Override
    public int calculateTotalCalories(Set<PizzaPatternIngredient> ingredients) {
        if (ingredients == null) throw new IllegalArgumentException("argument is null");
        if (ingredients.isEmpty()) return 0;

        double totalCalories = 0;
        for (PizzaPatternIngredient ingredient : ingredients) {
            totalCalories += ingredient.getIngredient().getCalories() * ((double) ingredient.getQuantity() / 100);
        }

        return (int) Math.round(totalCalories);
    }

    @Override
    public BigDecimal calculateTotalPrice(Set<PizzaPatternIngredient> ingredients) {
        if (ingredients == null) throw new IllegalArgumentException("argument is null");
        if (ingredients.isEmpty()) return BigDecimal.ZERO;

        double totalPrice = 0.0;
        for (PizzaPatternIngredient ingredient : ingredients) {
            totalPrice += ingredient.getIngredient().getPrice().doubleValue() * ((double) ingredient.getQuantity() / 100);
        }

        return BigDecimal.valueOf(totalPrice);
    }

    private Set<PizzaPatternIngredient> getPizzaPatternIngredients(Set<PizzaPatternIngredientCreateRequestDto> createDto) {
        Set<PizzaPatternIngredient> patternIngredients = new HashSet<>();

        for (PizzaPatternIngredientCreateRequestDto patternIngredientDto : createDto) {
            String title = patternIngredientDto.ingredientTitle();
            Ingredient ingredient = ingredientService.findByTitle(title);

            patternIngredients.add(PizzaPatternIngredient.builder()
                    .ingredient(ingredient)
                    .quantity(patternIngredientDto.quantity())
                    .build());
        }

        return patternIngredients;
    }
}