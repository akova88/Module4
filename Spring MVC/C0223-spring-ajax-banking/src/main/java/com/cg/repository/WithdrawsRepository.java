package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Withdraws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawsRepository extends JpaRepository<Withdraws, Long> {
}
