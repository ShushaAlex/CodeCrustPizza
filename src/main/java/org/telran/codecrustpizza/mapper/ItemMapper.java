package org.telran.codecrustpizza.mapper;

import org.springframework.stereotype.Component;
import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.entity.Category;
import org.telran.codecrustpizza.entity.Item;

@Component
public class ItemMapper {

    public Item toItem(ItemCreateRequestDto dto) {
        return Item.builder()
                .title(dto.title())
                .description(dto.description())
                .menuCategory(dto.menuCategory())
                .price(dto.price())
                .build();
    }

    public ItemResponseDto toDto(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategories()
                        .stream()
                        .map(Category::getTitle)
                        .toList()
        );
    }
}
