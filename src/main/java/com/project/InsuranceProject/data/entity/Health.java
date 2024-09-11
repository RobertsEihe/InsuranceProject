package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "health")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long health_id;

    @OneToOne
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    public Health(Long id, Risk risk) {
        this.risk = risk;
    }

    public Long getHealth_id() {
        return health_id;
    }

    public void setHealth_id(Long health_id) {
        this.health_id = health_id;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
}
