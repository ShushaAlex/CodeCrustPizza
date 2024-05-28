package org.telran.codecrustpizza.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.telran.codecrustpizza.entity.Phone;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.telran.codecrustpizza.testData.UserServiceTestData.COUNTRY_CODE_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.NUMBER_1;
import static org.telran.codecrustpizza.testData.UserServiceTestData.PHONE_1;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PhoneRepositoryTest {

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Phone phone;

    @BeforeEach
    public void setUp() {
        phone = PHONE_1;
        phone = phoneRepository.save(phone);

        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    public void tearDown() {
        phoneRepository.deleteAll();
    }

    @Test
    public void TestFindByCountryCodeAndNumberTest_WhenPhoneExists() {
        // Prepare data
        String countryCode = COUNTRY_CODE_1;
        String number = NUMBER_1;

        // Exec
        Optional<Phone> actualPhoneOptional = phoneRepository.findByCountryCodeAndNumber(countryCode, number);

        // Validate
        assertTrue(actualPhoneOptional.isPresent());
        Phone actualPhone = actualPhoneOptional.get();
        assertEquals(countryCode, actualPhone.getCountryCode());
        assertEquals(number, actualPhone.getNumber());
    }

    @Test
    public void TestFindByCountryCodeAndNumberTest_WhenPhoneNotExists() {
        // Prepare data
        String countryCode = "notExist";
        String number = "notExist";

        // Exec
        Optional<Phone> actualPhoneOptional = phoneRepository.findByCountryCodeAndNumber(countryCode, number);

        // Validate
        assertFalse(actualPhoneOptional.isPresent());
    }
}