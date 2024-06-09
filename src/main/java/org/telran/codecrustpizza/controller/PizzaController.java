package org.telran.codecrustpizza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Pizza Controller", description = "Operations related to pizza management")
public class PizzaController {

    private final PizzaService<PizzaResponseDto, PizzaCreateRequestDto, PizzaIngredient, Pizza> pizzaService;
    private final PizzaService<PizzaPatternResponseDto, PizzaPatternCreateDto, PizzaPatternIngredient, PizzaPattern> pizzaPatternService;

    @Operation(summary = "Find all pizzas", description = "Retrieve a list of all custom pizzas.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pizzas")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PizzaResponseDto> findAllPizza() {

        return pizzaService.findAll();
    }

    @Operation(summary = "Find all pizza patterns", description = "Retrieve a list of all pizza patterns, that can be used to create custom pizzas")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pizza patterns")
    @GetMapping("/pattern")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PizzaPatternResponseDto> findAllPattern() {

        return pizzaPatternService.findAll();
    }

    @Operation(summary = "Find pizza by ID", description = "Retrieve a pizza by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pizza"),
            @ApiResponse(responseCode = "400", description = "Pizza not found")
    })
    @GetMapping("/{pizzaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaResponseDto findPizzaById(@PathVariable Long pizzaId) {

        return pizzaService.getPizzaDtoById(pizzaId);
    }

    @Operation(summary = "Find pizza pattern by ID", description = "Retrieve a pizza pattern by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pizza pattern"),
            @ApiResponse(responseCode = "400", description = "Pizza pattern not found")
    })
    @GetMapping("pattern/{patternId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaPatternResponseDto findPatternById(@PathVariable Long patternId) {

        return pizzaPatternService.getPizzaDtoById(patternId);
    }

    @Operation(summary = "Create a new pizza", description = "Create a new custom pizza.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created pizza"),
            @ApiResponse(responseCode = "400", description = "Pizza pattern not found")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PizzaResponseDto createPizza(@Valid @RequestBody PizzaCreateRequestDto createDto) {

        return pizzaService.createPizza(createDto);
    }

    @Operation(summary = "Create a new pizza pattern", description = "Create a new pizza pattern. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created pizza pattern"),
            @ApiResponse(responseCode = "400", description = "Ingredient for pattern not found"),
            @ApiResponse(responseCode = "400", description = "Pattern already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping("/pattern")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PizzaPatternResponseDto createPizzaPattern(@Valid @RequestBody PizzaPatternCreateDto createDto) {

        return pizzaPatternService.createPizza(createDto);
    }

    @Operation(summary = "Update a pizza pattern", description = "Update an existing pizza pattern by its ID. Requires admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated pizza pattern"),
            @ApiResponse(responseCode = "400", description = "Pizza pattern not found"),
            @ApiResponse(responseCode = "400", description = "Ingredient for pattern not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/pattern/update/{patternId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PizzaPatternResponseDto updatePizzaPattern(@PathVariable Long patternId, @RequestBody PizzaPatternCreateDto createDto) {

        return pizzaPatternService.updatePizza(patternId, createDto);
    }
}