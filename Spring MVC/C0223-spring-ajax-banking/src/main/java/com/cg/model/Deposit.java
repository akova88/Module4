package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "deposits")
public class Deposit extends BaseEntity implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_Id", referencedColumnName = "id", nullable = false)
    private Customer customer;
//    @NotNull(message = "Vui lòng nhập số tiền cần nạp")
    @Column(precision = 10, scale = 0, nullable = false)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal transactionAmount;


    @Override
    public boolean supports(Class<?> aClass) {
        return Deposit.class.isAssignableFrom(aClass) ;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Deposit deposit = (Deposit) target;
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "deposit.zero");
        } else {
            if (transactionAmount.compareTo(BigDecimal.valueOf(0)) <= 0) {
                errors.rejectValue("transactionAmount", "deposit.min");
            } else {
                if (transactionAmount.compareTo(BigDecimal.valueOf(500000000)) > 0) {
                    errors.rejectValue("transactionAmount", "deposit.max");
                }
            }
        }
    }
}
