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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;
import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.telran.codecrustpizza.testData.ItemTestData.ITEM_CREATE_DTO;

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
    void getAllTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getMenuTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/item/menu"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PIZZAS", hasSize(3)))
                .andExpect(jsonPath("$.SALADS", hasSize(1)))
                .andExpect(jsonPath("$.DESSERTS", hasSize(1)));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getByIdTest() throws Exception {

        var result = mockMvc.perform(get("http://localhost:8080/api/item/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        String expectedTitle = "Four Cheese";

        assertEquals(expectedTitle, actualResponseDto.title());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void findItemsByCategoryTest() throws Exception {

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
    void saveItemTest() throws Exception {

        ItemCreateRequestDto createDto = ITEM_CREATE_DTO;

        var result = mockMvc.perform(post("http://localhost:8080/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("http://localhost:8080/api/item"))
                .andExpect(jsonPath("$.size()").value(6));

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        assertNotNull(actualDto.id());
        assertEquals(createDto.title(), actualDto.title());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void updateItemTest() throws Exception {

        ItemCreateRequestDto createDto = ITEM_CREATE_DTO;

        var result = mockMvc.perform(put("http://localhost:8080/api/item/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        assertEquals(1, actualDto.id());
        assertEquals(createDto.title(), actualDto.title());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void addCategory() throws Exception {

        int expectedCategoriesCount = 3;
        String categoryTitle = "new category";

        var result = mockMvc.perform(post("http://localhost:8080/api/item/1/category")
                        .param("category", categoryTitle)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        List<String> categories = actualDto.categories();

        assertEquals(expectedCategoriesCount, categories.size());
        assertEquals(1, actualDto.id());
        assertTrue(categories.contains(categoryTitle));
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(authorities = "ADMIN")
    void removeCategory() throws Exception {

        int expectedCategoriesCount = 1;
        String categoryTitle = "Vegetarian";

        var result = mockMvc.perform(delete("http://localhost:8080/api/item/1/category")
                        .param("category", categoryTitle)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ItemResponseDto actualDto = objectMapper.readValue(jsonResponse, ItemResponseDto.class);

        List<String> categories = actualDto.categories();

        assertEquals(expectedCategoriesCount, categories.size());
        assertFalse(categories.contains(categoryTitle));
    }
}