package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date payment_date;

    @Column(nullable = false)
    private String payment_type;

    @Column(nullable = false)
    private float amount;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    public Payment() {}

    public Payment(Date payment_date, String payment_type, float amount, Policy policy) {
        this.payment_date = payment_date;
        this.payment_type = payment_type;
        this.amount = amount;
        this.policy = policy;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
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
