package com.cg.api;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerResDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ValidateUtils validateUtils;
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        List<CustomerResDTO> customerResDTOS = new ArrayList<>();
        for (Customer item : customers) {
            CustomerResDTO customerResDTO = new CustomerResDTO();
            customerResDTO.setId(item.getId());
            customerResDTO.setFullName((item.getFullName()));
            customerResDTO.setEmail(item.getEmail());
            customerResDTO.setAddress(item.getAddress());
            customerResDTO.setPhone(item.getPhone());
            customerResDTO.setBalance(item.getBalance());

            customerResDTOS.add(customerResDTO);
        }
        return new ResponseEntity<>(customerResDTOS, HttpStatus.OK);
    }
    @GetMapping("/id_ne/{senderId}")
    public  ResponseEntity<?> getAllRecipients(@PathVariable Long senderId) {
        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        List<CustomerResDTO> recipientResDTOS = new ArrayList<>();
        for (Customer recipient : recipients) {
            CustomerResDTO recipientResDTO = new CustomerResDTO();
            recipientResDTO.setId(recipient.getId());
            recipientResDTO.setFullName((recipient.getFullName()));
            recipientResDTO.setEmail(recipient.getEmail());
            recipientResDTO.setAddress(recipient.getAddress());
            recipientResDTO.setPhone(recipient.getPhone());
            recipientResDTO.setBalance(recipient.getBalance());

            recipientResDTOS.add(recipientResDTO);
        }
        return new ResponseEntity<>(recipientResDTOS, HttpStatus.OK);
    }
    @GetMapping("/{customerId}")
    public  ResponseEntity<?> getById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findById((customerId));
        if (customerOptional.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "ID không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);

        Customer newCustomer = customerService.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @PatchMapping ("/{customerId}")
    public ResponseEntity<?> edit(@PathVariable Long customerId, @RequestBody CustomerResDTO customerResDTO, BindingResult bindingResult) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        Map<String, String> data = new HashMap<>();

        if (customerOptional.isEmpty()) {
            data.put("message", "ID không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Customer customer = customerOptional.get();
        new CustomerResDTO().validate(customerResDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        if (!validateUtils.isLetterWithoutNumberValid(customerResDTO.getFullName())) {
            data.put("message", "Tên không chứa ký tự số hoặc ký tự đặt biệt");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        customer.setFullName(customerResDTO.getFullName());
        String newEmail = customerResDTO.getEmail();
        if (!validateUtils.isEmailValid(newEmail)) {
            data.put("message", "email không đúng định dạng");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        if (customerService.existsByEmailAndIdNot(newEmail, customer.getId())) {
            data.put("message", "email đã tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        } else {
            customer.setEmail(newEmail);
            customer.setPhone(customerResDTO.getPhone());
            customer.setAddress(customerResDTO.getAddress());
            customerService.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }
}
