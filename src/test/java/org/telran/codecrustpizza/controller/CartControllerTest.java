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
import org.telran.codecrustpizza.dto.cart.CartResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void getCurrentUserCartItemsTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void addItem_IncreaseQuantity() throws Exception {

        int expectedQuantity = 3;
        int expectedCartItemsSize = 2;

        var result = mockMvc.perform(put("http://localhost:8080/api/cart/add")
                        .param("itemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CartResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, CartResponseDto.class);

        assertEquals(expectedQuantity, actualResponseDto.cartItems().getFirst().quantity());
        assertEquals(expectedCartItemsSize, actualResponseDto.cartItems().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void addItem_newCartItem() throws Exception {

        int expectedCartItemsSize = 3;

        var result = mockMvc.perform(put("http://localhost:8080/api/cart/add")
                        .param("itemId", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CartResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, CartResponseDto.class);

        assertEquals(expectedCartItemsSize, actualResponseDto.cartItems().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void removeItem_quantityReduction() throws Exception {
        int expectedQuantity = 1;
        int expectedCartItemsSize = 2;

        var result = mockMvc.perform(put("http://localhost:8080/api/cart/remove")
                        .param("itemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CartResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, CartResponseDto.class);

        assertEquals(expectedQuantity, actualResponseDto.cartItems().getFirst().quantity());
        assertEquals(expectedCartItemsSize, actualResponseDto.cartItems().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void removeItemTest() throws Exception {
        int expectedCartItemsSize = 1;

        var result = mockMvc.perform(put("http://localhost:8080/api/cart/remove")
                        .param("itemId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CartResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, CartResponseDto.class);

        assertEquals(expectedCartItemsSize, actualResponseDto.cartItems().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void clearCurrentUserCart() throws Exception {

        var result = mockMvc.perform(put("http://localhost:8080/api/cart/clear"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CartResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, CartResponseDto.class);

        assertTrue(actualResponseDto.cartItems().isEmpty());
    }
}