package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;

    @Column(nullable = false)
    private String type;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateLoss;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    public Claim() {}

    public Claim(String type, Date dateLoss, Date date, float amount, String description, Policy policy) {
        this.type = type;
        this.dateLoss = dateLoss;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.policy = policy;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateLoss() {
        return dateLoss;
    }

    public void setDateLoss(Date dateLoss) {
        this.dateLoss = dateLoss;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
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

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
// Getters and setters
}
