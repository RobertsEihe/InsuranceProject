package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "risk")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int risk_id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private float risk;

    @Column(nullable = false)
    private float rate;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;
    @OneToMany(mappedBy = "risk")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "risk")
    private List<House> houses;

    @OneToOne(mappedBy = "risk")
    private Health health;

    public Risk(int risk_id, String type, float risk, float rate, Policy policy, List<Vehicle> vehicles, List<House> houses, Health health) {
        this.risk_id = risk_id;
        this.type = type;
        this.risk = risk;
        this.rate = rate;
        this.policy = policy;
        this.vehicles = vehicles;
        this.houses = houses;
        this.health = health;
    }

    public Risk(int risk_id, String type, float risk, float rate) {
        this.risk_id = risk_id;
        this.type = type;
        this.risk = risk;
        this.rate = rate;
    }

    public int getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(int risk_id) {
        this.risk_id = risk_id;
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

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
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
