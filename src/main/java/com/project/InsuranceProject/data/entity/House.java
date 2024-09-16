package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long house_id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int year_built;

    @Column(nullable = false)
    private float area;

    @Column(nullable = false)
    private String mat_type;

    @ManyToOne
    @JoinColumn(name = "policy_risk_id")
    private Policy_risk policy_risk_id;

    public House() {}

    public House(String address, String type, int year_built, float area, String mat_type, Policy_risk policy_risk_id) {
        this.address = address;
        this.type = type;
        this.year_built = year_built;
        this.area = area;
        this.mat_type = mat_type;
        this.policy_risk_id = policy_risk_id;
    }

    public Long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(Long house_id) {
        this.house_id = house_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear_built() {
        return year_built;
    }

    public void setYear_built(int year_built) {
        this.year_built = year_built;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getMat_type() {
        return mat_type;
    }

    public void setMat_type(String mat_type) {
        this.mat_type = mat_type;
    }

    public Policy_risk getRisk() {
        return policy_risk_id;
    }

    public void setPolicyRisk(Policy_risk policy_risk_id) {
        this.policy_risk_id = policy_risk_id;
    }
}
