package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResDTO implements Validator {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;


    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerResDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerResDTO customerResDTO = (CustomerResDTO) o;
        String fullName = customerResDTO.fullName;

        if (fullName.length() < 5 || fullName.length() > 20) {
            errors.rejectValue("fullName", "fullName.length", "Tên từ 5 - 20 ký tự");
        }
    }
}


