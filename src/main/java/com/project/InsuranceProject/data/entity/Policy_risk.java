package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_risk")
@Inheritance(strategy = InheritanceType.JOINED)
public class Policy_risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policy_risk_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policy policy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_id")
    private Risk risk;

    @Column
    private double sum_insured;

    @Column
    private double total_premium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Policy_risk() {
    }

    public Policy_risk(Policy policy, Risk risk, double sum_insured, double total_premium, Vehicle vehicle) {
        this.policy = policy;
        this.risk = risk;
        this.sum_insured = sum_insured;
        this.total_premium = total_premium;
        this.vehicle = vehicle;
    }

    public Long getPolicy_risk_id() {
        return policy_risk_id;
    }

    public void setPolicy_risk_id(Long policy_risk_id) {
        this.policy_risk_id = policy_risk_id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public double getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(double sum_insured) {
        this.sum_insured = sum_insured;
    }

    public double getTotal_premium() {
        return total_premium;
    }

    public void setTotal_premium(double total_premium) {
        this.total_premium = total_premium;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}