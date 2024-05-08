package org.telran.codecrustpizza.service.impl;

import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;

import java.time.LocalDateTime;

public class TestData {

    public static LocalDateTime LOCAL_DATE_TIME_1 = LocalDateTime.of(1, 1, 1, 1, 1);

    public static UserResponseDto USER_RESPONSE_DTO_1 = new UserResponseDto(
            1L,
            "John Doe",
            "john.doe@example.com",
            null,
            null,
            false,
            LOCAL_DATE_TIME_1);

    public static User USER_1 = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .password("1234")
            .creationDate(LOCAL_DATE_TIME_1)
            .build();
}

