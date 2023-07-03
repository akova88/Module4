package com.cg.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "withdraws")
public class Withdraws extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_Id", referencedColumnName = "id", nullable = false)
    private Customer customer;
@Column(precision = 10, scale = 0, nullable = false)
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
}