package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraws;
import com.cg.model.dto.CustomerCreReqDTO;
import com.cg.model.dto.CustomerCreResDTO;
import com.cg.model.dto.CustomerResDTO;
import com.cg.model.dto.CustomerUpdReqDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAllByIdNot(Long id);
    List<CustomerResDTO> findAllCustomerResDTO();

    CustomerCreResDTO create(CustomerCreReqDTO customerCreReqDTO);

    Customer update(Customer customer, CustomerUpdReqDTO customerUpdReqDTO);

    Customer deposit(Deposit deposit);

    Customer withdraw(Withdraws withdraws);
    Transfer transfer(Transfer transfer);

    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdNot(String email,Long id);
}
