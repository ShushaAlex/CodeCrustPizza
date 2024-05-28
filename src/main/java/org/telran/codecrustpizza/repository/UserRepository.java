package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.favoritePizzas WHERE u.id = :id")
    Optional<User> findByIdWithFavorites(Long id);

    @EntityGraph(value = "User.withPhones", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findById(Long id);
}