package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private float amount;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    public Payment() {}

    public Payment(Date paymentDate, String paymentType, float amount, Policy policy) {
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.amount = amount;
        this.policy = policy;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
// Getters and setters
}
