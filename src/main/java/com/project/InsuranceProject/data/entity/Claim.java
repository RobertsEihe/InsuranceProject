package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claim_id;

    @Column
    private String type;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate date_loss;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate date;

    @Column
    private double amount;

    @Column
    private String description;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    public Claim() {}

    public Claim(String type, LocalDate date_loss, LocalDate date, float amount, String description, Policy policy) {
        this.type = type;
        this.date_loss = date_loss;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.policy = policy;
    }

    public Long getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(Long claim_id) {
        this.claim_id = claim_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate_loss() {
        return date_loss;
    }

    public void setDate_loss(LocalDate date_loss) {
        this.date_loss = date_loss;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

}
