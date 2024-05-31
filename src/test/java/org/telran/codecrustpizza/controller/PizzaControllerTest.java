package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void findPizzaById() {
    }

    @Test
    void findPatternById() {
    }

    @Test
    void createPizza() {
    }

    @Test
    void createPizzaPattern() {
    }

    @Test
    void updatePizzaPattern() {
    }

    @Test
    void deletePizza() {
    }
}