package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;

import java.util.List;

/**
 * Service interface for managing items in an application.
 */
public interface ItemService {

    /**
     * Retrieves a list of all items from db.
     *
     * @return a list of {@link ItemResponseDto} representing all items.
     */
    List<ItemResponseDto> getAll();

    /**
     * Finds an item by its ID.
     *
     * @param id the ID of the item to be found.
     * @return an {@link ItemResponseDto} representing the found item.
     */
    ItemResponseDto findById(Long id);

    /**
     * Finds all items by their category.
     *
     * @param category the category of the items to be found.
     * @return a list of {@link ItemResponseDto} representing the found items.
     */
    List<ItemResponseDto> findAllByCategory(String category);

    /**
     * Saves a new item.
     *
     * @param createDto the DTO containing information for creating the item.
     * @return an {@link ItemResponseDto} representing the saved item.
     */
    ItemResponseDto saveItem(ItemCreateRequestDto createDto);

    /**
     * Updates an existing item.
     *
     * @param id               the ID of the item to be updated.
     * @param createRequestDto the DTO containing updated information for the item.
     * @return an {@link ItemResponseDto} representing the updated item.
     */
    ItemResponseDto updateItem(Long id, ItemCreateRequestDto createRequestDto);

    /**
     * Adds a category to an item.
     *
     * @param id the ID of the item to which the category will be added.
     * @param category the category to be added.
     * @return an {@link ItemResponseDto} representing the updated item.
     */
    ItemResponseDto addCategory(Long id, String category);

    /**
     * Removes a category from an item.
     *
     * @param id the ID of the item from which the category will be removed.
     * @param category the category to be removed.
     * @return an {@link ItemResponseDto} representing the updated item.
     */
    ItemResponseDto removeCategory(Long id, String category);

    /**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to be deleted.
     * @return a boolean indicating whether the item was successfully deleted.
     */
    boolean deleteItem(Long id);
}