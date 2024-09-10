package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claim_id;

    @Column(nullable = false)
    private String type;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date_loss;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "policyId", nullable = false)
    private Policy policy;

    public Claim() {}

    public Claim(String type, Date date_loss, Date date, float amount, String description, Policy policy) {
        this.type = type;
        this.date_loss = date_loss;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.policy = policy;
    }

    public int getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(int claim_id) {
        this.claim_id = claim_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate_loss() {
        return date_loss;
    }

    public void setDate_loss(Date date_loss) {
        this.date_loss = date_loss;
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
