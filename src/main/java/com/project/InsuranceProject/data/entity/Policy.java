package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policy_id;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate start_date;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate end_date;

    @Column
    private int duration;

    @Column
    private boolean aut_renewal;

    @Column
    private String status;

    @Column
    private String ur_status;

    @Column
    private String agent_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // This is the foreign key column
    private Users users;

    @OneToMany(mappedBy = "policy")
    private List<Claim> claims;

    @OneToMany(mappedBy = "policy")
    private List<Document> documents;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy_risk> policy_risks;

    public Policy() {}

    public Policy(LocalDate start_date, LocalDate end_date, int duration, boolean aut_renewal, String status, String ur_status, String agent_id,
                  Users users) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.duration = duration;
        this.aut_renewal = aut_renewal;
        this.status = status;
        this.ur_status = ur_status;
        this.agent_id = agent_id;
        this.users = users;

    }

    public Policy(LocalDate now, LocalDate localDate, int i, boolean b, String ur, String pending, Object o) {
    }

    public Long getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(Long policy_id) {
        this.policy_id = policy_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getAgentId() {
        return agent_id;
    }

    public void setAgentId(String agent_id) {
        this.agent_id = agent_id;
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
}
