package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraws;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.withdraws.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostPersist;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    @Autowired
    IDepositService depositService;
    @Autowired
    IWithdrawService withdrawService;
    @Autowired
    ITransferService transferService;
    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/list";
    }
    @GetMapping("/create")
    public String showCreate() {
        return "customer/create";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tôn tại");
            return "customer/deposit";

        } else {
            Customer customer = customerOptional.get();
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);

            model.addAttribute("deposit", deposit);
        }
        return "customer/deposit";
    }

    @GetMapping("/withdraws/{customerId}")
    public String showWithdrawsPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tôn tại");
            return "customer/withdraws";

        } else {
            Customer customer = customerOptional.get();
            Withdraws withdraws = new Withdraws();
            withdraws.setCustomer(customer);

            model.addAttribute("withdraws", withdraws);
        }
        return "customer/withdraws";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Optional<Customer> senderOptional = customerService.findById(senderId);
        if (senderOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender not found");
            return "customer/transfer";

        } else {
            Customer sender = senderOptional.get();
            Transfer transfer = new Transfer();
            transfer.setSender(sender);
            model.addAttribute("transfer", transfer);
            List<Customer> recipients = customerService.findAll();
            model.addAttribute("recipients", recipients);
        }
        return "customer/transfer";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer, Model model) {
        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        model.addAttribute("success", true);
        model.addAttribute("messageS", "Successfully added new customers");
        return "customer/create";
    }

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@ModelAttribute Deposit deposit, @PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tôn tại");
        } else {
            Customer customer = customerOptional.get();

            deposit.setId(null);
            depositService.save(deposit);

            BigDecimal currentBalance = customer.getBalance();
            BigDecimal newBalance = currentBalance.add(deposit.getTransactionAmount().abs());
            customer.setBalance(newBalance);
            customerService.save(customer);

            deposit.setCustomer(customer);
            model.addAttribute("deposit", deposit);
            model.addAttribute("success", true);
            model.addAttribute("messageS", "Successful deposit transaction");
        }
        return "customer/deposit";
    }

    @PostMapping("/withdraws/{customerId}")
    public String doWithdraws(@ModelAttribute Withdraws withdraws, @PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tồn tại");
            return "customer/withdraws";
        } else {
            Customer customer = customerOptional.get();

            BigDecimal currentBalance = customer.getBalance();
            if (currentBalance.compareTo(withdraws.getTransactionAmount()) == -1) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Customer's balance not enough to make a withdrawal");
                return "customer/withdraws";
            } else {
                withdraws.setId(null);
                withdrawService.save(withdraws);
                BigDecimal newBalance = currentBalance.subtract(withdraws.getTransactionAmount().abs());
                customer.setBalance(newBalance);
                customerService.save(customer);
                withdraws.setCustomer(customer);
                model.addAttribute("withdraws", withdraws);
                model.addAttribute("success", true);
                model.addAttribute("messageS", "Successful withdraws transaction");
            }
        }
        return "customer/withdraws";
    }
    @PostMapping("/transfer/{senderId}")
    public String doTransfer (@PathVariable Long senderId, @ModelAttribute Transfer transfer, Model model) {
        Optional<Customer> senderOptional = customerService.findById(senderId);
        List<Customer> recipients = customerService.findAll();
        model.addAttribute("recipients", recipients);
        if (senderOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender not found");
        }
        Customer sender = senderOptional.get();
                Long recipientId = transfer.getRecipient().getId();
        Optional<Customer> recipientOptional = customerService.findById(recipientId);
        if (recipientOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Recipient not valid");
            return "customer/transfer";
        }
        Customer recipient = recipientOptional.get();
        if (senderId.equals(recipientId)) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender ID must be different from Recipient ID");
            return "customer/transfer";
        }


        BigDecimal senderCurrentBalance = senderOptional.get().getBalance();
        BigDecimal transferAmount = transfer.getTransferAmount();
        Long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (senderCurrentBalance.compareTo(transactionAmount) < 0) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender balance not enough to transfer");
            return "customer/transfer";
        }
        recipient.setBalance(transferAmount.add(recipient.getBalance()));
        sender.setBalance(senderCurrentBalance.subtract(transactionAmount));
        transfer.setId(null);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        transfer.setRecipient(recipient);
        customerService.save(sender);
        customerService.save(recipient);
        transferService.save(transfer);
        model.addAttribute("success", true);
        model.addAttribute("messageS", "Successful transfer");
        return "customer/transfer";
    }
}
