package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WithdrawCreReqDTO implements Validator {
    private String transactionAmountWdr;
    @Override
    public boolean supports(Class<?> aClass) {
        return WithdrawCreReqDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        WithdrawCreReqDTO withdrawCreReqDTO = (WithdrawCreReqDTO) o;
        String transactionAmountWdrStr = withdrawCreReqDTO.transactionAmountWdr;

        if (transactionAmountWdrStr == null) {
            errors.rejectValue("transactionAmountWdr", "transactionAmountWdr.length", "Số tiền cần rút là bắt buộc");
            return;
        }

        if (transactionAmountWdrStr.length() == 0) {
            errors.rejectValue("transactionAmountWdr", "transactionAmountWdr.length", "Vui lòng nhập số tiền muốn rút");
        }
        else {
            if (!transactionAmountWdrStr.matches("\\d+")) {
                errors.rejectValue("transactionAmountWdr", "transactionAmountWdr.matches", "Vui lòng nhập giá trị tiền bằng chữ số");
            }
            else {
                BigDecimal transactionAmountWdr = BigDecimal.valueOf(Long.parseLong(transactionAmountWdrStr));

                if (transactionAmountWdr.compareTo(BigDecimal.valueOf(100L)) <= 0) {
                    errors.rejectValue("transactionAmountWdr", "transactionAmountWdr.min", "Số tiền muốn rút thấp nhất là $100");
                }
                else {
                    if (transactionAmountWdr.compareTo(BigDecimal.valueOf(1000000L)) > 0) {
                        errors.rejectValue("transactionAmountWdr", "transactionAmountWdr.max", "Số tiền muốn rút tối đa là $1.000.000");
                    }
                }
            }
        }
    }
}
