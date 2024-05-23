package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public List<PizzaResponseDto> findAll() {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PizzaResponseDto createPizza(PizzaCreateRequestDto pizzaCreateDto) {
        Optional<Pizza> existingPizza = pizzaRepository.findByTitle(pizzaCreateDto.title());
        if (existingPizza.isPresent()) throw new EntityException(ENTITY_EXIST.getCustomMessage("Pizza"));

        PizzaPattern pizzaPattern = pizzaPatternRepository.findById(pizzaCreateDto.patternId())
                .orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("PizzaPattern", pizzaCreateDto.patternId())));

        // ToDo replace to mapper with arg PizzaPattern pizzaPattern
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
    @Transactional
    public PizzaResponseDto getPizzaById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isEmpty()) throw new EntityException(NO_SUCH_ID.getCustomMessage("Pizza", id));

        return pizzaMapper.toDto(pizza.get());
    }

    @Override
    @Transactional
    public PizzaResponseDto updatePizza(Long id, PizzaCreateRequestDto pizzaCreateDto) {
        //TODO как сделать заглушку? / 2 interfaces or plug with exception
        return null;
    }

    @Override
    @Transactional
    public boolean deletePizza(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(id);
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

    public Set<PizzaIngredient> calculateIngredientQuantity(Pizza pizza) {
        double sizeDif = calculateSizeDif(pizza.getPizzaPattern().getSize(), pizza.getSize());
        Set<PizzaPatternIngredient> patternIngredients = pizza.getPizzaPattern().getPatternIngredients();

        Set<PizzaIngredient> pizzaIngredients = new HashSet<>();

        for (PizzaPatternIngredient patternIngredient : patternIngredients) {
            PizzaIngredient pizzaIngredient = PizzaIngredient.builder()
                    .ingredient(patternIngredient.getIngredient())
                    .pizza(pizza)
                    .build();

            pizzaIngredient.setQuantity((int) Math.round(patternIngredient.getQuantity() * (sizeDif + 1)));

            pizzaIngredients.add(pizzaIngredient);
        }

        return pizzaIngredients;
    }

    public double calculateSizeDif(int patternSize, int pizzaSize) {
        if (pizzaSize == patternSize) return 0.0;

        double patternRadius = patternSize / 2.0;
        double pizzaRadius = pizzaSize / 2.0;

        double patternArea = Math.PI * Math.pow(patternRadius, 2);
        double pizzaArea = Math.PI * Math.pow(pizzaRadius, 2);

        double difference = pizzaArea - patternArea;
        double ratio = difference / pizzaArea;

        ratio = Math.round(ratio * 100) / 100.0;

        return ratio;
    }
}