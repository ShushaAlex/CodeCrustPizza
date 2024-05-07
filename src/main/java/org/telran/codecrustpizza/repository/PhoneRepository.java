package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
