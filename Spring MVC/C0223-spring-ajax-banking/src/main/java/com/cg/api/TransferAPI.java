package com.cg.api;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferCreReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferAPI {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ValidateUtils validateUtils;

    @PostMapping("/{senderId}")
    public ResponseEntity<?> transfer(@PathVariable("senderId") String senderIdStr, @RequestBody TransferCreReqDTO transferCreReqDTO, BindingResult bindingResult) {
        Map<String, String> data = new HashMap<>();

        new TransferCreReqDTO().validate(transferCreReqDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        if (!validateUtils.isNumberValid(senderIdStr) ) {
            data.put("message", "Mã sender không hợp lệ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        String recipientIdStr = transferCreReqDTO.getRecipientId();
        if (!validateUtils.isNumberValid(recipientIdStr) ) {
            data.put("message", "Mã recipient không hợp lệ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Long senderId = Long.parseLong(senderIdStr);
        Long recipientId = Long.parseLong(recipientIdStr);

        Optional<Customer> senderOpt = customerService.findById(senderId);
        Optional<Customer> recipientOpt = customerService.findById(recipientId);
        if (senderOpt.isEmpty()) {
            data.put("message", "Mã sender không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        if (recipientOpt.isEmpty()) {
            data.put("message", "Mã recipient không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Customer sender = senderOpt.get();
        Customer recipient = recipientOpt.get();

        BigDecimal senderCurrentBalance = sender.getBalance();
        BigDecimal transferMoney = BigDecimal.valueOf(Long.parseLong(transferCreReqDTO.getTransferMoney()));
        Long fees = 10L;
        BigDecimal feesAmount = transferMoney.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferMoney.add(feesAmount);

        if (senderCurrentBalance.compareTo(transactionAmount) < 0) {
            data.put("message", "Số dư tài khoản không đủ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Transfer transfer = new Transfer();
        transfer.setId(null);
        transfer.setTransferAmount(transferMoney);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        transfer.setRecipient(recipient);
        transfer.setSender(sender);

        customerService.transfer(transfer);

        List<Customer> customers = new ArrayList<>();
        customers.add(transfer.getSender());
        customers.add(transfer.getRecipient());

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
