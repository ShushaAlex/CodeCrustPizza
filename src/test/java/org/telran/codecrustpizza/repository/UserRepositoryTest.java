package org.telran.codecrustpizza.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.telran.codecrustpizza.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.telran.codecrustpizza.testData.UserServiceTestData.USER_1;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    public void setUp() {
        user = USER_1;
        user = userRepository.save(user);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindByIdWithPhones() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        assertThat(foundUser.get().getFavoritePizzas()).isNotNull();
    }

    @Test
    public void testFindByIdWithFavoritePizzasEntityGraph() {
        Optional<User> foundUser = userRepository.findByIdWithFavoritePizzas(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        assertThat(foundUser.get().getPhones()).isNotNull();
    }
}