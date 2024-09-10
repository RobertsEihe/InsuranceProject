package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policy_id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date start_date;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private boolean aut_renewal;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String ur_status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToMany(mappedBy = "policy")
    private List<Claim> claims;

    @OneToMany(mappedBy = "policy")
    private List<Document> documents;

    public Policy() {}

    public Policy(Date start_date, Date endDate, int duration, boolean aut_renewal, String status, String ur_status,
                  Customer customer, Agent agent) {
        this.start_date = start_date;
        this.endDate = endDate;
        this.duration = duration;
        this.aut_renewal = aut_renewal;
        this.status = status;
        this.ur_status = ur_status;
        this.customer = customer;
        this.agent = agent;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAut_renewal() {
        return aut_renewal;
    }

    public void setAut_renewal(boolean aut_renewal) {
        this.aut_renewal = aut_renewal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUr_status() {
        return ur_status;
    }

    public void setUr_status(String ur_status) {
        this.ur_status = ur_status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
// Getters and setters
}
