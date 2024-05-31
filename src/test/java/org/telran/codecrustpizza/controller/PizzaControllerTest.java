package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;

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
    void findAllPizza() {
    }

    @Test
    void findAllPattern() {
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