package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicle_id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int odd;

    @Column(nullable = false)
    private float current_value;

    @ManyToOne
    @JoinColumn(name = "policy_risk_id")
    private Policy_risk policy_risk_id;

    public Vehicle() {}

    public Vehicle(String make, String model, int year, int odd, float current_value, Policy_risk policy_risk_id) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.odd = odd;
        this.current_value = current_value;
        this.policy_risk_id = policy_risk_id;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOdd() {
        return odd;
    }

    public void setOdd(int odd) {
        this.odd = odd;
    }

    public float getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(float current_value) {
        this.current_value = current_value;
    }

    public Policy_risk getRisk() {
        return policy_risk_id;
    }

    public void setPolicyRisk(Policy_risk policy_risk_id) {
        this.policy_risk_id = policy_risk_id;
    }
}
