package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "risk")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riskId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private float risk;

    @Column(nullable = false)
    private float rate;

    @Column(nullable = false)
    private int policyId;

    @OneToMany(mappedBy = "risk")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "risk")
    private List<House> houses;

    @OneToOne(mappedBy = "risk")
    private Health health;

    public Risk() {}

    public Risk(String type, float risk, float rate, int policyId) {
        this.type = type;
        this.risk = risk;
        this.rate = rate;
        this.policyId = policyId;
    }

    public int getRiskId() {
        return riskId;
    }

    public void setRiskId(int riskId) {
        this.riskId = riskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getRisk() {
        return risk;
    }

    public void setRisk(float risk) {
        this.risk = risk;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }
// Getters and setters
}
