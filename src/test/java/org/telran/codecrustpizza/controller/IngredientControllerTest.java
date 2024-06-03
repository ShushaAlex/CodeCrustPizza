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
import org.telran.codecrustpizza.dto.ingredient.IngredientCreateRequestDto;
import org.telran.codecrustpizza.dto.ingredient.IngredientResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.telran.codecrustpizza.testData.IngredientServiceTestData.INGREDIENT_CREATE_DTO_1;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllTestTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(6));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getByIdTest() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/ingredient/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        IngredientResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, IngredientResponseDto.class);

        String expectedTitle = "Mozzarella Cheese";

        assertEquals(expectedTitle, actualResponseDto.title());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void saveIngredientTest() throws Exception {
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;

        var result = mockMvc.perform(post("http://localhost:8080/api/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        IngredientResponseDto actual = objectMapper.readValue(jsonResponse, IngredientResponseDto.class);

        assertNotNull(actual.id());
        assertEquals(createDto.title(), actual.title());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void updateIngredientTest() throws Exception {
        IngredientCreateRequestDto createDto = INGREDIENT_CREATE_DTO_1;

        var result = mockMvc.perform(put("http://localhost:8080/api/ingredient/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        IngredientResponseDto actual = objectMapper.readValue(jsonResponse, IngredientResponseDto.class);

        assertEquals(1, actual.id());
        assertEquals(createDto.title(), actual.title());
    }
}