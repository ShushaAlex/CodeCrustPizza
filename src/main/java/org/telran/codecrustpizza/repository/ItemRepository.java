package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i JOIN FETCH i.categories WHERE i.title = :title")
    Optional<Item> findByTitle(String title);

    @Query("SELECT i FROM Item i JOIN FETCH i.categories WHERE i.id = :id")
    Optional<Item> findById(Long id);

    @Query("SELECT i FROM Item i JOIN FETCH i.categories c WHERE c.title = :category")
    List<Item> findByCategory(String category);

}
