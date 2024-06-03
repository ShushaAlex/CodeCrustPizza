package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getAll() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getMenu() {

    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getById() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/item/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        String expectedTitle = "Margherita";

        assertEquals(expectedTitle, actualResponseDto.title());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findItemsByCategory() throws Exception {

        String categoryName = "Meat Lovers";
        int expectedCount = 3;

        mockMvc.perform(get("http://localhost:8080/api/item/category")
                        .param("category", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(expectedCount));
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void saveItem() {
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void updateItem() {
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void addCategory() {
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void removeCategory() {
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void deleteItem() {
    }
}