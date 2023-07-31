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
public class TransferCreReqDTO implements Validator {
    private String transferMoney;
    private String recipientId;
    public boolean supports(Class<?> aClass) {
        return TransferCreReqDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TransferCreReqDTO transferCreReqDTO = (TransferCreReqDTO) o;
        String transferMoney = transferCreReqDTO.transferMoney;

        if (transferMoney == null) {
            errors.rejectValue("transferMoney", "transferMoney.length", "Số tiền cần chuyển là bắt buộc");
            return;
        }

        if (transferMoney.length() == 0) {
            errors.rejectValue("transferMoney", "transferMoney.length", "Vui lòng nhập số tiền muốn chuyển");
        }
        else {
            if (!transferMoney.matches("\\d+")) {
                errors.rejectValue("transferMoney", "transferMoney.matches", "Vui lòng nhập giá trị tiền bằng chữ số");
            }
            else {
                BigDecimal transferMoneyNum = BigDecimal.valueOf(Long.parseLong(transferMoney));

                if (transferMoneyNum.compareTo(BigDecimal.valueOf(100L)) <= 0) {
                    errors.rejectValue("transferMoney", "transferMoney.min", "Số tiền chuyển thấp nhất là $100");
                }
                else {
                    if (transferMoneyNum.compareTo(BigDecimal.valueOf(1000000L)) > 0) {
                        errors.rejectValue("transferMoney", "transferMoney.max", "Số tiền chuyển tối đa là $1.000.000");
                    }
                }
            }
        }
    }
}
