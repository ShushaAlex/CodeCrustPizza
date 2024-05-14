package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.entity.Pizza;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.PizzaMapper;
import org.telran.codecrustpizza.repository.PizzaPatternRepository;
import org.telran.codecrustpizza.repository.PizzaRepository;
import org.telran.codecrustpizza.service.PizzaService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
public class PizzaServiceImpl implements PizzaService<PizzaResponseDto, PizzaCreateRequestDto, PizzaIngredient> {

    private final PizzaRepository pizzaRepository;
    private final PizzaPatternRepository pizzaPatternRepository;
    private final PizzaMapper pizzaMapper;

    @Autowired
    public PizzaServiceImpl(PizzaRepository pizzaRepository, PizzaPatternRepository pizzaPatternRepository, PizzaMapper pizzaMapper) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPatternRepository = pizzaPatternRepository;
        this.pizzaMapper = pizzaMapper;
    }

    @Override
    public List<PizzaResponseDto> findAll() {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::toDto)
                .toList();
    }

    @Override
    public PizzaResponseDto createPizza(PizzaCreateRequestDto pizzaCreateDto) {
        Optional<Pizza> existingPizza = pizzaRepository.findByTitle(pizzaCreateDto.title());
        if (existingPizza.isPresent()) throw new EntityException(ENTITY_EXIST.getCustomMessage("Pizza"));

        PizzaPattern pizzaPattern = pizzaPatternRepository.findByIdWithIngredients(pizzaCreateDto.patternId())
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", pizzaCreateDto.patternId())));

        Pizza pizza = Pizza.builder()
                .title(pizzaCreateDto.title())
                .size(pizzaCreateDto.size())
                .dough(pizzaPattern.getDough())
                .description(pizzaPattern.getDescription())
                .pizzaPattern(pizzaPattern)
                .build();

        pizza.setPizzaIngredients(calculateIngredientQuantity(pizza));
        pizza.setPrice(calculateTotalPrice(pizza.getPizzaIngredients()));
        pizza.setCalories(calculateTotalCalories(pizza.getPizzaIngredients()));

        pizza = pizzaRepository.save(pizza);

        return pizzaMapper.toDto(pizza);
    }

    @Override
    public PizzaResponseDto getPizzaById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findByIdWithIngredients(id);
        if (pizza.isEmpty()) throw new EntityException(NO_SUCH_ID.getCustomMessage("Pizza", id));

        return pizzaMapper.toDto(pizza.get());
    }

    @Override
    public PizzaResponseDto updatePizza(Long id, PizzaCreateRequestDto pizzaCreateDto) {
        //TODO как сделать заглушку?
        return null;
    }

    @Override
    public boolean deletePizza(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findByIdWithIngredients(id);
        if (pizza.isEmpty()) throw new EntityException(NO_SUCH_ID.getCustomMessage("Pizza", id));

        pizzaRepository.delete(pizza.get());
        return true;
    }

    @Override
    public int calculateTotalCalories(Set<PizzaIngredient> ingredients) {
        if (ingredients == null) throw new IllegalArgumentException("argument is null");
        if (ingredients.isEmpty()) return 0;

        double totalCalories = 0;
        for (PizzaIngredient ingredient : ingredients) {
            totalCalories += ingredient.getIngredient().getCalories() * ((double) ingredient.getQuantity() / 100);
        }

        return (int) Math.round(totalCalories);
    }

    @Override
    public BigDecimal calculateTotalPrice(Set<PizzaIngredient> ingredients) {
        if (ingredients == null) throw new IllegalArgumentException("argument is null");
        if (ingredients.isEmpty()) return BigDecimal.ZERO;

        double totalPrice = 0.0;
        for (PizzaIngredient ingredient : ingredients) {
            totalPrice += ingredient.getIngredient().getPrice().doubleValue() * ((double) ingredient.getQuantity() / 100);
        }

        return BigDecimal.valueOf(totalPrice);
    }

    private Set<PizzaIngredient> calculateIngredientQuantity(Pizza pizza) {
        double sizeDif = calculateSizeDif(pizza.getPizzaPattern().getSize(), pizza.getSize());
        Set<PizzaPatternIngredient> patternIngredients = pizza.getPizzaPattern().getPatternIngredients();

        Set<PizzaIngredient> pizzaIngredients = new HashSet<>();

        for (PizzaPatternIngredient patternIngredient : patternIngredients) {
            PizzaIngredient pizzaIngredient = PizzaIngredient.builder()
                    .ingredient(patternIngredient.getIngredient())
                    .pizza(pizza)
                    .build();

            if (sizeDif == 0.0) {
                pizzaIngredient.setQuantity(patternIngredient.getQuantity());
            } else {
                pizzaIngredient.setQuantity((int) Math.round(patternIngredient.getQuantity() * (sizeDif + 1)));
            }

            pizzaIngredients.add(pizzaIngredient);
        }

        return pizzaIngredients;
    }

    private double calculateSizeDif(int patternSize, int pizzaSize) {
        if (pizzaSize == patternSize) return 0.0;

        double diff = Math.abs(patternSize - pizzaSize);
        double avg = (double) (patternSize + patternSize) / 2;

        return (diff / avg);
    }
}
