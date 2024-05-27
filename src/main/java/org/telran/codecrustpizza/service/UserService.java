package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.address.AddressCreateRequestDto;
import org.telran.codecrustpizza.dto.phone.PhoneCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserChangePasswordRequestDto;
import org.telran.codecrustpizza.dto.user.UserCreateRequestDto;
import org.telran.codecrustpizza.dto.user.UserResponseDto;
import org.telran.codecrustpizza.entity.User;
import org.telran.codecrustpizza.entity.enums.Role;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves a list of all user response DTOs.
     *
     * @return a list of {@link UserResponseDto} representing all users.
     */
    List<UserResponseDto> findAll();

    /**
     * Retrieves a user response DTO by its ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return a {@link UserResponseDto} representing the user.
     */
    UserResponseDto getUserDtoById(Long id);

    /**
     * Retrieves a user entity by its ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the {@link User} entity.
     */
    User getById(Long id);

    /**
     * Retrieves a user response DTO by email.
     *
     * @param email the email of the user to be retrieved.
     * @return a {@link UserResponseDto} representing the user.
     */
    UserResponseDto getUserDtoByEmail(String email);

    /**
     * Retrieves a user entity by email.
     *
     * @param email the email of the user to be retrieved.
     * @return the {@link User} entity.
     */
    User getByEmail(String email);

    /**
     * Saves a new user.
     *
     * @param dto the DTO containing information for creating the user.
     * @return a {@link UserResponseDto} representing the saved user.
     */
    UserResponseDto save(UserCreateRequestDto dto);

    /**
     * Assigns a role to a user.
     *
     * @param userId the ID of the user to be assigned the role.
     * @param role   the role to be assigned.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto assignRole(Long userId, Role role);

    /**
     * Adds a phone to a user.
     *
     * @param userId the ID of the user to be updated.
     * @param phoneDto the DTO containing phone information.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto addPhone(Long userId, PhoneCreateRequestDto phoneDto);

    /**
     * Removes a phone from a user.
     *
     * @param userId the ID of the user to be updated.
     * @param phoneId the ID of the phone to be removed.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto removePhone(Long userId, Long phoneId);

    /**
     * Adds an address to a user.
     *
     * @param userId the ID of the user to be updated.
     * @param addressDto the DTO containing address information.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto addAddress(Long userId, AddressCreateRequestDto addressDto);

    /**
     * Removes an address from a user.
     *
     * @param userId the ID of the user to be updated.
     * @param addressId the ID of the address to be removed.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto removeAddress(Long userId, Long addressId);

    /**
     * Changes the password of a user.
     *
     * @param userId the ID of the user to be updated.
     * @param changePasswordRequestDto the DTO containing password change information.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto changePassword(Long userId, UserChangePasswordRequestDto changePasswordRequestDto);

    /**
     * Changes the email of a user.
     *
     * @param userId the ID of the user to be updated.
     * @param newEmail the new email to be set.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto changeEmail(Long userId, String newEmail);

    /**
     * Changes the name of a user.
     *
     * @param userId the ID of the user to be updated.
     * @param name the new name to be set.
     * @return a {@link UserResponseDto} representing the updated user.
     */
    UserResponseDto changeName(Long userId, String name);

    /**
     * Retrieves the ID of the currently authenticated user.
     *
     * @return the ID of the current user.
     */
    Long getCurrentUserId();

    //TODO добавить методы по добавлению и удалению любимой пиццы
}