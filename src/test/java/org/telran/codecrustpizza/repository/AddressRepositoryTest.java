package org.telran.codecrustpizza.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.telran.codecrustpizza.entity.Address;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.telran.codecrustpizza.testData.UserServiceTestData.ADDRESS_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.CITY_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.HOUSE_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.STREET_1;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Address address;

    @BeforeEach
    public void setUp() {
        address = ADDRESS_1;
        address = addressRepository.save(address);

        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    public void testFindByCityAndStreetAndHouse_WhenAddressExists() {
        // Prepare data
        String city = CITY_1;
        String street = STREET_1;
        String house = HOUSE_1;

        // Exec
        Optional<Address> actualAddressOptional = addressRepository.findByCityAndStreetAndHouse(city, street, house);

        // Validate
        assertTrue(actualAddressOptional.isPresent());
        Address actualAddress = actualAddressOptional.get();
        assertEquals(city, actualAddress.getCity());
        assertEquals(street, actualAddress.getStreet());
        assertEquals(house, actualAddress.getHouse());
    }

    @Test
    public void testFindByCityAndStreetAndHouse_WhenAddressDoesNotExist_ThenReturnEmptyOptional() {
        // Prepare data
        String city = "NonExistingCity";
        String street = "NonExistingStreet";
        String house = "NonExistingHouse";

        // Exec
        Optional<Address> actualAddressOptional = addressRepository.findByCityAndStreetAndHouse(city, street, house);

        // Validate
        assertFalse(actualAddressOptional.isPresent());
    }
}