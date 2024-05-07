package org.telran.codecrustpizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.codecrustpizza.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
