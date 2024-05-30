package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.telran.codecrustpizza.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;


    @Test
    void registerUser() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void changeEmail() {
    }

    @Test
    void changeName() {
    }

    @Test
    void assignRole() {
    }

    @Test
    void addPhone() {
    }

    @Test
    void removePhone() {
    }

    @Test
    void addAddress() {
    }

    @Test
    void removeAddress() {
    }

    @Test
    void addFavoritePizzaToCurrentUser() {
    }

    @Test
    void removeFavoritePizzaFromCurrentUser() {
    }
}