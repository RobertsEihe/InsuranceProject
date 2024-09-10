package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "health")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int health_id;

    @OneToOne
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    public Health(int health_id, Risk risk) {
        this.health_id = health_id;
        this.risk = risk;
    }

    public int getHealth_id() {
        return health_id;
    }

    public void setHealth_id(int health_id) {
        this.health_id = health_id;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    // Getters and setters
}
