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
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    public House() {}

    public House(String address, String type, int year_built, float area, String mat_type, Risk risk) {
        this.address = address;
        this.type = type;
        this.year_built = year_built;
        this.area = area;
        this.mat_type = mat_type;
        this.risk = risk;
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

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
}
