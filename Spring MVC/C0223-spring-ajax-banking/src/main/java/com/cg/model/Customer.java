package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Họ tên là bắt buộc")
    @Pattern(regexp = "^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)", message = "Tên phải là ký tự A-Z")
    @Column(name = "full_name",nullable = false )
    private String fullName;
    @NotBlank(message = "Email là bắt buộc")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$", message = "Email có định dạng abc@abc.com")
    @Column(nullable = false, unique = true)
    private String email;
    @Pattern(regexp = "^\\+\\d{1,2}\\s\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message = "Phone không đúng định dạng")
    private String phone;
    private String address;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;



    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.isAssignableFrom(aClass) ;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        String fullName = customer.fullName;
        if (fullName.length() < 5 || fullName.length() > 20) {
            errors.rejectValue("fullName", "fullName.length", "Tên phải từ 5-20 ký tự");
        }
    }


}
