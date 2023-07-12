package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraws;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAllByIdNot(Long id);
    Customer deposit(Deposit deposit);

    Customer withdraw(Withdraws withdraws);
    Transfer transfer(Transfer transfer);

    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdNot(String email,Long id);
}
