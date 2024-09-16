package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.SimpleTimeZone;

@Entity
@Table(name = "risk")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long risk_id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String risk;

    @Column(nullable = false)
    private double rate;

    @OneToMany(mappedBy = "risk", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy_risk> policy_risk;

    public Risk() {
    }

    public Risk(String type, String risk, double rate, List<Policy_risk> policy_risk) {

        this.type = type;
        this.risk = risk;
        this.rate = rate;
        this.policy_risk = policy_risk;
    }

    public Risk( String type, String risk, double rate) {
        this.type = type;
        this.risk = risk;
        this.rate = rate;
    }

    public Long getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(Long risk_id) {
        this.risk_id = risk_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<Policy_risk> getPolicy_risk() {
        return policy_risk;
    }

    public void setPolicy_risk(List<Policy_risk> policy_risk) {
        this.policy_risk = policy_risk;
    }
}
