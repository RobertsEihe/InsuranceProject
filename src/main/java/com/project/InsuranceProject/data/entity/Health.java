package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "health")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long health_id;

    @OneToOne
    @JoinColumn(name = "policy_risk_id")
    private Policy_risk policy_risk_id;

    public Health(Long id, Policy_risk policy_risk) {
        this.policy_risk_id = policy_risk;
    }

    public Long getHealth_id() {
        return health_id;
    }

    public void setHealth_id(Long health_id) {
        this.health_id = health_id;
    }

    public Policy_risk getRisk() {
        return policy_risk_id;
    }

    public void setPolicyRisk(Policy_risk policy_risk_id) {
        this.policy_risk_id = policy_risk_id;
    }
}
