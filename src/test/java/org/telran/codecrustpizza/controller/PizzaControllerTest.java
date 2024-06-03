package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;
import org.telran.codecrustpizza.dto.pizza.PizzaCreateRequestDto;
import org.telran.codecrustpizza.dto.pizza.PizzaResponseDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternCreateDto;
import org.telran.codecrustpizza.dto.pizza.pizzaPattern.PizzaPatternResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.telran.codecrustpizza.testData.PizzaTestData.CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.PizzaTestData.PATTERN_CREATE_DTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findAllPizzaTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/pizza"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findAllPatternTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/pizza/pattern"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findPizzaByIdTest() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/pizza/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        PizzaResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, PizzaResponseDto.class);

        String expectedTitle = "Four Cheese";

        assertEquals(expectedTitle, actualResponseDto.title());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findPatternById() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/pizza/pattern/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        PizzaPatternResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, PizzaPatternResponseDto.class);

        String expectedTitle = "Margherita";

        assertEquals(expectedTitle, actualResponseDto.title());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void createPizzaTest() throws Exception {

        PizzaCreateRequestDto createDto = CREATE_DTO_1;

        var result = mockMvc.perform(post("http://localhost:8080/api/pizza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        PizzaResponseDto actual = objectMapper.readValue(jsonResponse, PizzaResponseDto.class);

        assertEquals(createDto.title(), actual.title());
        assertNotNull(actual.id());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void createPizzaPatternTest() throws Exception {

        PizzaPatternCreateDto createDto = PATTERN_CREATE_DTO;

        var result = mockMvc.perform(post("http://localhost:8080/api/pizza/pattern")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        PizzaPatternResponseDto actual = objectMapper.readValue(jsonResponse, PizzaPatternResponseDto.class);

        assertNotNull(actual.id());
        assertEquals(createDto.title(), actual.title());
        assertEquals(createDto.patternIngredients().size(), actual.patternIngredients().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void updatePizzaPatternTest() throws Exception {

        PizzaPatternCreateDto createDto = PATTERN_CREATE_DTO;

        var result = mockMvc.perform(put("http://localhost:8080/api/pizza/pattern/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        PizzaPatternResponseDto actual = objectMapper.readValue(jsonResponse, PizzaPatternResponseDto.class);

        assertEquals(1, actual.id());
        assertEquals(createDto.title(), actual.title());
        assertEquals(createDto.patternIngredients().size(), actual.patternIngredients().size());
    }

    @Test
    void deletePizzaTest() {
    }
}