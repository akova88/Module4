package com.cg.model;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "withdraws")
public class Withdraws extends BaseEntity implements Validator{
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

    public Withdraws() {
    }

    public Withdraws(Long id, Customer customer, BigDecimal transactionAmount) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Withdraws.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Withdraws withdraws = (Withdraws) target;
        BigDecimal transactionAmount = withdraws.getTransactionAmount();
        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "withdraw.zero");
        } else {
            if (transactionAmount.compareTo(BigDecimal.valueOf(0)) <= 0) {
                errors.rejectValue("transactionAmount", "withdraw.min");
            } else {
                if (transactionAmount.compareTo(BigDecimal.valueOf(500000000)) > 0) {
                    errors.rejectValue("transactionAmount", "withdraw.max");
                }
            }
        }
    }
}
