package org.telran.codecrustpizza.service;

import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;

import java.util.List;

public interface ItemService {

    List<ItemResponseDto> getAll();

    ItemResponseDto findById(Long id);

    List<ItemResponseDto> findAllByCategory(String category);

    ItemResponseDto saveItem(ItemCreateRequestDto createDto);

    ItemResponseDto updateItem(Long id, ItemCreateRequestDto createRequestDto);

    ItemResponseDto addCategory(Long id, String category);

    ItemResponseDto removeCategory(Long id, String category);

    boolean deleteItem(Long id);
}