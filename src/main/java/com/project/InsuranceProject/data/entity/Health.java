package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "health")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int healthId;

    @OneToOne
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    public Health() {}

    public Health(Risk risk) {
        this.risk = risk;
    }

    public int getHealthId() {
        return healthId;
    }

    public void setHealthId(int healthId) {
        this.healthId = healthId;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
// Getters and setters
}
