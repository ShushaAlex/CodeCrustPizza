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
import org.telran.codecrustpizza.dto.order.OrderResponseDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "jane.smith@example.com", authorities = {"ADMIN", "USER"})
    void getCurrentUserOrdersTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getOrderById() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/order/2"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        OrderResponseDto actual = objectMapper.readValue(jsonResponse, OrderResponseDto.class);

        assertEquals(2, actual.id());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "jane.smith@example.com", authorities = {"ADMIN", "USER"})
    void createOrder() throws Exception {

        String expectedOrderStatus = "IN_PROCESSING";
        int expectedOrderItemsCount = 2;

        var result = mockMvc.perform(post("http://localhost:8080/api/order/create")
                        .param("addressId", "3"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("http://localhost:8080/api/cart"))
                .andExpect(jsonPath("$.size()").value(0));

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        OrderResponseDto actual = objectMapper.readValue(jsonResponse, OrderResponseDto.class);

        assertEquals(expectedOrderItemsCount, actual.items().size());
        assertEquals(expectedOrderStatus, actual.status());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void cancelOrder() throws Exception {

        String expectedOrderStatus = "CANCELED";

        var result = mockMvc.perform(put("http://localhost:8080/api/order/2/cancel"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        OrderResponseDto actual = objectMapper.readValue(jsonResponse, OrderResponseDto.class);

        assertEquals(expectedOrderStatus, actual.status());

    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void updateOrderStatus() throws Exception {

        String expectedOrderStatus = "PAYED";

        var result = mockMvc.perform(put("http://localhost:8080/api/order/2/updateStatus")
                        .param("status", expectedOrderStatus))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        OrderResponseDto actual = objectMapper.readValue(jsonResponse, OrderResponseDto.class);

        assertEquals(expectedOrderStatus, actual.status());
    }
}