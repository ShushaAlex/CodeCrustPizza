package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // JOIN FETCH подтягивает связи many to many и one to many
    @Query("SELECT u FROM User u JOIN FETCH u.favoritePizzas WHERE u.id = :id")
    Optional<User> findByIdWithFavorites(Long id);
}
