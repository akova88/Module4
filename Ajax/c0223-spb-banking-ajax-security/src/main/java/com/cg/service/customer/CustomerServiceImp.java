package com.cg.service.customer;

import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CustomerServiceImp implements ICustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private WithdrawsRepository withdrawsRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<CustomerResDTO> findAllCustomerResDTO() {
        return customerRepository.findAllCustomerResDTO();
    }

    @Override
    public CustomerCreResDTO create(CustomerCreReqDTO customerCreReqDTO) {
        LocationRegionCreReqDTO locationRegionCreReqDTO = customerCreReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionCreReqDTO.toLocationRegion();
        locationRegionRepository.save(locationRegion);

        Customer customer = customerCreReqDTO.toCustomer();
        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);
        CustomerCreResDTO customerCreResDTO = customer.toCustomerCreResDTO();
        return customerCreResDTO;
    }

    @Override
    public Customer update(Customer customer, CustomerUpdReqDTO customerUpdReqDTO) {
        LocationRegionUpdReqDTO locationRegionUpdReqDTO = customerUpdReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionUpdReqDTO.toLocationRegion();
        locationRegionRepository.save(locationRegion);

        Customer customerUpdate = customerUpdReqDTO.toCustomer(customer.getId(), locationRegion);
        customerUpdate.setBalance(customer.getBalance());
        customerRepository.save(customerUpdate);
        return customerUpdate;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer deposit(Deposit deposit) {
        deposit.setId(null);
        depositRepository.save(deposit);

        Customer customer = deposit.getCustomer();
        BigDecimal transactionAmount = deposit.getTransactionAmount();


        BigDecimal newBalance = customer.getBalance().add(transactionAmount);
        customerRepository.setBalance(customer.getId(), newBalance);

        customer.setBalance(newBalance);
        return customer;
    }

    @Override
    public Customer withdraw(Withdraws withdraws) {
        withdraws.setId(null);
        withdrawsRepository.save(withdraws);

        Customer customer = withdraws.getCustomer();
        BigDecimal transactionAmount = withdraws.getTransactionAmount();

        BigDecimal newBalance = customer.getBalance().subtract(transactionAmount);
        customerRepository.setBalance(customer.getId(), newBalance);

        customer.setBalance(newBalance);
        return customer;
    }

    public Transfer transfer(Transfer transfer) {

        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();

        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transactionAmount = transfer.getTransactionAmount();

        BigDecimal newBalanceSender = sender.getBalance().subtract(transactionAmount);
        BigDecimal newBalanceRecipient = recipient.getBalance().add(transferAmount);

        customerRepository.setBalance(sender.getId(), newBalanceSender);
        customerRepository.setBalance(recipient.getId(), newBalanceRecipient );

        sender.setBalance(newBalanceSender);
        transfer.setSender(sender);
        recipient.setBalance(newBalanceRecipient);
        transfer.setRecipient(recipient);

        transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByEmailAndIdNot(String email, Long id) {
        return customerRepository.existsByEmailAndIdNot(email, id);
    }
}
