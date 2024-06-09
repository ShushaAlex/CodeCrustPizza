package org.telran.codecrustpizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;
import org.telran.codecrustpizza.dto.JwtAuthenticationResponse;
import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.dto.user.UserSignInRequestDto;
import org.telran.codecrustpizza.dto.user.UserWithFavoritePizzaResponseDto;
import org.telran.codecrustpizza.security.AuthenticationService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.telran.codecrustpizza.testData.UserServiceTestData.ADDRESS_CREATE_DTO_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.PHONE_CREATE_DTO_1;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void findAllTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @Transactional
    @Rollback
    void registerUserSuccessTest() throws Exception {
        UserChangePasswordRequestDto passwordDto = new UserChangePasswordRequestDto("SecureP@ssw0rd", "SecureP@ssw0rd");
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Name", "name@name.com", passwordDto);

        var result = mockMvc.perform(post("http://localhost:8080/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertNotNull(actual.id());
        assertNotNull(actual.name());
        assertNotNull(actual.email());
    }

    @Test
    @Transactional
    @Rollback
    void registerUserPasswordCheckFailTest() throws Exception {
        UserChangePasswordRequestDto passwordDto = new UserChangePasswordRequestDto("SecureP@ssw0rd", "SecureP@ssw0r");
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Name", "name@name.com", passwordDto);

        mockMvc.perform(post("http://localhost:8080/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    void registerUserValidationFailTest() throws Exception {
        UserChangePasswordRequestDto passwordDto = new UserChangePasswordRequestDto("SecureP@ssw0rd", "SecureP@ssw0rd");
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "", "name@name.com", passwordDto);

        var result = mockMvc.perform(post("http://localhost:8080/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\": \"name must not be blank\"}"));

    }

    @Test
    void loginSuccessfulAuthenticationTest() throws Exception {

        UserSignInRequestDto validRequest = new UserSignInRequestDto("testuser", "testpassword");

        JwtAuthenticationResponse validResponse = new JwtAuthenticationResponse();
        validResponse.setToken("test-jwt-token");

        when(authenticationService.authenticate(any(UserSignInRequestDto.class))).thenReturn(validResponse);

        mockMvc.perform(post("http://localhost:8080/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest))
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\": \"test-jwt-token\"}"));
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void getCurrentUserTest() throws Exception {

        String email = "john.doe@example.com";

        var result = mockMvc.perform(get("http://localhost:8080/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(email, actual.email());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void changeEmailTest() throws Exception {
        String newEmail = "test@test.com";

        var result = mockMvc.perform(put("http://localhost:8080/api/user/change-email")
                        .param("email", newEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(newEmail, actual.email());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void changePasswordTest() throws Exception {
        UserChangePasswordRequestDto passwordDto = new UserChangePasswordRequestDto("SecureP@ssw0rd", "SecureP@ssw0rd");

        var result = mockMvc.perform(put("http://localhost:8080/api/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertNotNull(actual.id());
        assertNotNull(actual.name());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void changeNameTest() throws Exception {
        String newName = "new Name";

        var result = mockMvc.perform(put("http://localhost:8080/api/user/change-name")
                        .param("name", newName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actual = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(newName, actual.name());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void assignRoleSuccessTest() throws Exception {

        mockMvc.perform(put("http://localhost:8080/api/user/1/assign-role")
                        .param("role", "USER"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void assignRoleForbidden() throws Exception {

        mockMvc.perform(put("http://localhost:8080/api/user/1/assign-role")
                        .param("role", "USER"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void addPhoneTest() throws Exception {

        var resultBeforeAddingPhone = mockMvc.perform(get("http://localhost:8080/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponseBeforeAddingPhone = resultBeforeAddingPhone.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto userDtoBeforeAddingPhone = objectMapper.readValue(jsonResponseBeforeAddingPhone, UserResponseDto.class);

        PhoneCreateRequestDto phoneCreateRequestDto = PHONE_CREATE_DTO_1;

        var result = mockMvc.perform(put("http://localhost:8080/api/user/phone/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneCreateRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(userDtoBeforeAddingPhone.phones().size() + 1, actualResponseDto.phones().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void removePhoneTest() throws Exception {

        var resultBeforeRemoving = mockMvc.perform(get("http://localhost:8080/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponseBeforeRemoving = resultBeforeRemoving.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto userDtoBeforeRemoving = objectMapper.readValue(jsonResponseBeforeRemoving, UserResponseDto.class);

        var result = mockMvc.perform(put("http://localhost:8080/api/user/phone/remove")
                        .param("phoneId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(userDtoBeforeRemoving.phones().size() - 1, actualResponseDto.phones().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void addAddressTest() throws Exception {

        var resultBeforeAdding = mockMvc.perform(get("http://localhost:8080/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponseBeforeAdding = resultBeforeAdding.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto userDtoBeforeAdding = objectMapper.readValue(jsonResponseBeforeAdding, UserResponseDto.class);

        AddressCreateRequestDto addressDto = ADDRESS_CREATE_DTO_1;

        var result = mockMvc.perform(put("http://localhost:8080/api/user/address/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(userDtoBeforeAdding.addresses().size() + 1, actualResponseDto.addresses().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "john.doe@example.com", authorities = {"ADMIN", "USER"})
    void removeAddress() throws Exception {
        var resultBeforeRemoving = mockMvc.perform(get("http://localhost:8080/api/user"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponseBeforeRemoving = resultBeforeRemoving.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto userDtoBeforeRemoving = objectMapper.readValue(jsonResponseBeforeRemoving, UserResponseDto.class);

        var result = mockMvc.perform(put("http://localhost:8080/api/user/address/remove")
                        .param("addressId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        assertEquals(userDtoBeforeRemoving.addresses().size() - 1, actualResponseDto.addresses().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "jane.smith@example.com", authorities = {"ADMIN", "USER"})
    void addFavoritePizzaToCurrentUserTest() throws Exception {

        int expectedPizzaCount = 3;

        var result = mockMvc.perform(put("http://localhost:8080/api/user/favorites/add")
                        .param("pizzaId", "2"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserWithFavoritePizzaResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserWithFavoritePizzaResponseDto.class);

        assertEquals(expectedPizzaCount, actualResponseDto.favoritePizzas().size());
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "jane.smith@example.com", authorities = {"ADMIN", "USER"})
    void removeFavoritePizzaFromCurrentUserTest() throws Exception {

        int expectedPizzaCount = 1;

        var result = mockMvc.perform(put("http://localhost:8080/api/user/favorites/remove")
                        .param("pizzaId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserWithFavoritePizzaResponseDto actualResponseDto = objectMapper.readValue(jsonResponse, UserWithFavoritePizzaResponseDto.class);

        assertEquals(expectedPizzaCount, actualResponseDto.favoritePizzas().size());
    }

}