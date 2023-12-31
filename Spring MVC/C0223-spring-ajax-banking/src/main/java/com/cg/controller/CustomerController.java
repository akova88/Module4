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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public String showCreate( Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @GetMapping("/edit/{idStr}")
    public String showEditPage(@PathVariable String idStr, Model model) {
        try {
            Long id = Long.parseLong(idStr);
            Optional<Customer> customerOptional = customerService.findById(id);
            if (customerOptional.isEmpty()) {
                model.addAttribute("error", true);
                model.addAttribute("message", "ID khách hàng không tồn tại");
            } else {
                Customer customer = customerOptional.get();
                model.addAttribute("customer", customer);
            }
            return "customer/edit";
        } catch (Exception e) {
            return "error/404";
        }
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


//------------------------------------POST----------------------------------------------//


    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute Customer customer, BindingResult bindingResult,Model model) {

        new Customer().validate(customer, bindingResult);
        if (bindingResult.hasErrors()) {
            return "customer/create";
        }
        String email = customer.getEmail();
        Boolean existsEmail = customerService.existsByEmail(email);

        if (existsEmail) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Email is already existed");
            return "customer/create";
        }

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("success", true);
        model.addAttribute("messageS", "Successfully added new customers");
        return "customer/create";
    }

    @PostMapping("/edit/{id}")
    public String doUpdate(@Valid @ModelAttribute Customer customer,BindingResult bindingResult, Model model, @PathVariable Long id) {
        new Customer().validate(customer, bindingResult);
        if (bindingResult.hasErrors()) {
            return "customer/edit";
        }
        String email = customer.getEmail();
        Boolean existsEmail = customerService.existsByEmailAndIdNot(email, id);

        if (existsEmail) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Email is already existed");
            return "customer/create";
        }
        customer.setId(id);
        customerService.save(customer);
        model.addAttribute("success", true);
        model.addAttribute("messageS", "Successfully update customers");

        return ("/customer/edit");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            Long customerId = Long.parseLong(id);
            customerService.deleteById(customerId);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "deleteMess");
            return "redirect:/customers";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@Valid @ModelAttribute Deposit deposit, BindingResult bindingResult, Model model, @PathVariable Long customerId) {

        new Deposit().validate(deposit, bindingResult);
        if (bindingResult.hasErrors()) {
            return "customer/deposit";
        }
        try {

            Optional<Customer> customerOptional = customerService.findById(customerId);
            if (customerOptional.isEmpty()) {
                model.addAttribute("error", true);
                model.addAttribute("message", "ID khách hàng không tôn tại");
            } else {

                Customer customer = customerService.deposit(deposit);
//            Customer customer = customerOptional.get();
//            deposit.setId(null);
//            depositService.save(deposit);
//
//            BigDecimal currentBalance = customer.getBalance();
//            BigDecimal newBalance = currentBalance.add(deposit.getTransactionAmount().abs());
//            customer.setBalance(newBalance);
//            customerService.save(customer);
//            BigDecimal transactionAmount = deposit.getTransactionAmount();
//            customerService.incrementBalance(customerId, transactionAmount);
//
                deposit.setCustomer(customer);
                model.addAttribute("deposit", deposit);
                model.addAttribute("success", true);
                model.addAttribute("messageS", "Successful deposit transaction");
            }
            return "customer/deposit";
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Lỗi định dạng số");
            return "customer/deposit";
        }
    }

    @PostMapping("/withdraws/{customerId}")
    public String doWithdraws(@Validated @ModelAttribute Withdraws withdraws, BindingResult bindingResult, Model model , @PathVariable Long customerId) {
        new Withdraws().validate(withdraws, bindingResult);
        if (bindingResult.hasErrors()) {
            return "customer/withdraws";
        }
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tồn tại");
            return "customer/withdraws";
        } else {
//            Customer customer = customerOptional.get();

            BigDecimal currentBalance = withdraws.getCustomer().getBalance();
            if (currentBalance.compareTo(withdraws.getTransactionAmount()) == -1) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Customer's balance not enough to make a withdrawal");
                return "customer/withdraws";
            } else {

//                withdraws.setId(null);
//                withdrawService.save(withdraws);
//                BigDecimal newBalance = currentBalance.subtract(withdraws.getTransactionAmount().abs());
//                customer.setBalance(newBalance);
//                customerService.save(customer);
                Customer customer = customerService.withdraw(withdraws);
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


        BigDecimal senderCurrentBalance = sender.getBalance();
        BigDecimal transferAmount = transfer.getTransferAmount().abs();
        Long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (senderCurrentBalance.compareTo(transactionAmount) < 0) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Sender balance not enough to transfer");
            return "customer/transfer";
        }
//        recipient.setBalance(transferAmount.add(recipient.getBalance()));
//        sender.setBalance(senderCurrentBalance.subtract(transactionAmount));
        transfer.setId(null);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        transfer.setRecipient(recipient);
        customerService.transfer(transfer);

//        customerService.save(sender);
//        customerService.save(recipient);
//        transferService.save(transfer);
        model.addAttribute("success", true);
        model.addAttribute("messageS", "Successful transfer");
        return "customer/transfer";
    }
}
