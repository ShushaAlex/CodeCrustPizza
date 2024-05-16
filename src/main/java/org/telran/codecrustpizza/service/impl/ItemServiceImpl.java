package org.telran.codecrustpizza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telran.codecrustpizza.dto.item.ItemCreateRequestDto;
import org.telran.codecrustpizza.dto.item.ItemResponseDto;
import org.telran.codecrustpizza.entity.Item;
import org.telran.codecrustpizza.exception.EntityException;
import org.telran.codecrustpizza.mapper.ItemMapper;
import org.telran.codecrustpizza.repository.ItemRepository;
import org.telran.codecrustpizza.service.ItemService;

import java.util.List;
import java.util.Optional;

import static org.telran.codecrustpizza.exception.ExceptionMessage.ENTITY_EXIST;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_CATEGORY;
import static org.telran.codecrustpizza.exception.ExceptionMessage.NO_SUCH_ID;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public List<ItemResponseDto> getAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .toList();
    }

    @Override
    public ItemResponseDto findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", id)));

        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemResponseDto> findAllByCategory(String category) {
        List<Item> items = itemRepository.findByCategory(category);
        if (items.isEmpty()) {
            throw new EntityException(NO_SUCH_CATEGORY.getCustomMessage("item", category));
        }

        return items.stream()
                .map(itemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ItemResponseDto saveItem(ItemCreateRequestDto createDto) {
        Optional<Item> existingItem = itemRepository.findByTitle(createDto.title());
        if (existingItem.isPresent()) throw new EntityException(ENTITY_EXIST.getCustomMessage("item"));

        Item item = itemMapper.toItem(createDto);
        item = itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    @Override
    @Transactional
    public ItemResponseDto updateItem(Long id, ItemCreateRequestDto createDto) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", id)));

        item.setTitle(createDto.title());
        item.setDescription(createDto.description());
        item.setPrice(createDto.price());

        item = itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    @Override
    @Transactional
    public ItemResponseDto addCategory(Long id, String category) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", id)));
        return null;
    }

    @Override
    @Transactional
    public ItemResponseDto removeCategory(Long id, String category) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", id)));
        return null;
    }

    @Override
    @Transactional
    public ItemResponseDto deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityException(NO_SUCH_ID.getCustomMessage("item", id)));
        return null;
    }
}
