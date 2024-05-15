package org.telran.codecrustpizza.testData;

import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.Phone;
import org.telran.codecrustpizza.entity.User;

import java.time.LocalDateTime;

public class UserServiceTestData {
    // 1 set of user data
    public static LocalDateTime LOCAL_DATE_TIME_1 = LocalDateTime.of(2023, 1, 1, 1, 1);
    public static String EMAIL_1 = "john.doe@example.com";
    public static String NAME_1 = "John Doe";
    public static String PASSWORD_1 = "1234";
    // 2 set of user data
    public static LocalDateTime LOCAL_DATE_TIME_2 = LocalDateTime.of(2022, 5, 5, 12, 30);
    public static String EMAIL_2 = "jane.smith@example.com";
    public static String NAME_2 = "Jane Smith";
    public static String PASSWORD_2 = "4321";
    // 1 set of phone data
    public static String TITLE_1 = "mobile";
    public static String COUNTRY_CODE_1 = "+1";
    public static String AREA_CODE_1 = "123";
    public static String NUMBER_1 = "456789";
    public static String EXTENSION_1 = "12";
    // 1 set of address data
    public static String CITY_1 = "city1";
    public static String STREET_1 = "street1";
    public static String HOUSE_1 = "house1";
    public static String COMMENT_1 = "comment1";

    public static UserResponseDto USER_RESPONSE_DTO_1 = new UserResponseDto(
            1L,
            NAME_1,
            EMAIL_1,
            null,
            null,
            false,
            LOCAL_DATE_TIME_1);

    public static User USER_1 = User.builder()
            .id(1L)
            .name(NAME_1)
            .email(EMAIL_1)
            .password(PASSWORD_1)
            .creationDate(LOCAL_DATE_TIME_1)
            .build();

    public static UserChangePasswordRequestDto CHANGE_PASSWORD_DTO_1 = new UserChangePasswordRequestDto(
            PASSWORD_1,
            PASSWORD_2
    );

    public static UserCreateRequestDto USER_CREATE_DTO_1 = new UserCreateRequestDto(
            NAME_1,
            EMAIL_1,
            CHANGE_PASSWORD_DTO_1
    );

    public static PhoneCreateRequestDto PHONE_CREATE_DTO_1 = new PhoneCreateRequestDto(
            TITLE_1,
            COUNTRY_CODE_1,
            AREA_CODE_1,
            NUMBER_1,
            EXTENSION_1
    );

    public static Phone PHONE_1 = Phone.builder()
            .id(1L)
            .title(TITLE_1)
            .countryCode(COUNTRY_CODE_1)
            .areaCode(AREA_CODE_1)
            .number(NUMBER_1)
            .extension(EXTENSION_1)
            .build();

    public static AddressCreateRequestDto ADDRESS_CREATE_DTO_1 = new AddressCreateRequestDto(
            CITY_1,
            STREET_1,
            HOUSE_1,
            COMMENT_1
    );
}

