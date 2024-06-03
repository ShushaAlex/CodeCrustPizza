package org.telran.codecrustpizza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;
import org.telran.codecrustpizza.entity.Pizza;
import org.telran.codecrustpizza.entity.PizzaIngredient;
import org.telran.codecrustpizza.entity.PizzaPattern;
import org.telran.codecrustpizza.entity.PizzaPatternIngredient;
import org.telran.codecrustpizza.service.PizzaService;

import java.util.List;

@RestController
@RequestMapping("api/pizza")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService<PizzaResponseDto, PizzaCreateRequestDto, PizzaIngredient, Pizza> pizzaService;
    private final PizzaService<PizzaPatternResponseDto, PizzaPatternCreateDto, PizzaPatternIngredient, PizzaPattern> pizzaPatternService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PizzaResponseDto> findAllPizza() {

        return pizzaService.findAll();
    }

    @GetMapping("/pattern")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PizzaPatternResponseDto> findAllPattern() {

        return pizzaPatternService.findAll();
    }

    @GetMapping("/{pizzaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaResponseDto findPizzaById(@PathVariable Long pizzaId) {

        return pizzaService.getPizzaDtoById(pizzaId);
    }

    @GetMapping("pattern/{patternId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaPatternResponseDto findPatternById(@PathVariable Long patternId) {

        return pizzaPatternService.getPizzaDtoById(patternId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaResponseDto createPizza(@Valid @RequestBody PizzaCreateRequestDto createDto) {

        return pizzaService.createPizza(createDto);
    }

    @PostMapping("/pattern")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PizzaPatternResponseDto createPizzaPattern(@Valid @RequestBody PizzaPatternCreateDto createDto) {

        return pizzaPatternService.createPizza(createDto);
    }

    @PutMapping("/pattern/update/{patternId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PizzaPatternResponseDto updatePizzaPattern(@PathVariable Long patternId, @RequestBody PizzaPatternCreateDto createDto) {

        return pizzaPatternService.updatePizza(patternId, createDto);
    }

    @DeleteMapping("/delete/{pizzaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deletePizza(@PathVariable Long pizzaId) {

        return pizzaService.deletePizza(pizzaId);
    }

}