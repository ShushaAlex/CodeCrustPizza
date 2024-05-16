package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.entity.enums.Dough;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.PizzaPatternMapper;
import org.telran.codecrustpizza.repository.PizzaPatternRepository;
import org.telran.codecrustpizza.service.PizzaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
public class PizzaPatternServiceImpl implements PizzaService<PizzaPatternResponseDto, PizzaPatternCreateDto, PizzaPatternIngredient> {

    private final PizzaPatternRepository pizzaPatternRepository;
    private final PizzaPatternMapper pizzaPatternMapper;

    @Autowired
    public PizzaPatternServiceImpl(PizzaPatternRepository pizzaPatternRepository, PizzaPatternMapper pizzaPatternMapper) {
        this.pizzaPatternRepository = pizzaPatternRepository;
        this.pizzaPatternMapper = pizzaPatternMapper;
    }

    @Override
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

        PizzaPattern pizzaPattern = pizzaPatternMapper.toPizzaPattern(pizzaCreateDto);
        pizzaPattern.setCalories(calculateTotalCalories(pizzaPattern.getPatternIngredients()));
        pizzaPattern.setPrice(calculateTotalPrice(pizzaPattern.getPatternIngredients()));

        pizzaPattern = pizzaPatternRepository.save(pizzaPattern);

        return pizzaPatternMapper.toDto(pizzaPattern);
    }

    @Override
    public PizzaPatternResponseDto getPizzaById(Long id) {
        Optional<PizzaPattern> pizzaPatternOptional = pizzaPatternRepository.findById(id);
        if (pizzaPatternOptional.isEmpty()) throw new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", id));

        return pizzaPatternMapper.toDto(pizzaPatternOptional.get());
    }

    @Override
    @Transactional
    public PizzaPatternResponseDto updatePizza(Long id, PizzaPatternCreateDto pizzaCreateDto) {
        PizzaPattern pizzaPattern = pizzaPatternRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", id)));

        pizzaPattern.setTitle(pizzaCreateDto.title());
        pizzaPattern.setDescription(pizzaCreateDto.description());
        pizzaPattern.setSize(pizzaCreateDto.size());
        pizzaPattern.setDough(Dough.valueOf(pizzaCreateDto.dough()));
        pizzaPattern.setPatternIngredients(pizzaCreateDto.patternIngredients());
        pizzaPattern.setCalories(calculateTotalCalories(pizzaPattern.getPatternIngredients()));
        pizzaPattern.setPrice(calculateTotalPrice(pizzaPattern.getPatternIngredients()));

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
}