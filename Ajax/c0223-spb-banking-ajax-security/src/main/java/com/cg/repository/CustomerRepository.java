package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByIdNot(Long id);

    @Query("SELECT NEW com.cg.model.dto.CustomerResDTO(cus.id, cus.fullName, cus.email, cus.phone, cus.balance, cus.locationRegion) FROM Customer AS cus WHERE cus.deleted = false")
    List<CustomerResDTO> findAllCustomerResDTO();

    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdNot(String email,Long id);

    @Modifying
    @Query("UPDATE Customer AS c SET c.balance = :newBalance WHERE c.id = :id")
    void setBalance(@Param("id") Long id, @Param("newBalance") BigDecimal newBalance);
}
