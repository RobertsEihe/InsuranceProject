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

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private boolean aut_renewal;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String urStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    // Recovered agent_id as a simple column
    @Column(nullable = true)
    private Long agent_id;

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER)
    private List<Claim> claims;

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER)
    private List<Document> documents;

    public Policy() {}

    public Policy(LocalDate start_date, LocalDate end_date, int duration, boolean aut_renewal, String status, String urStatus,
        Users users, Long agent_id) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.duration = duration;
        this.aut_renewal = aut_renewal;
        this.status = status;
        this.urStatus = urStatus;
        this.users = users;
        this.agent_id = agent_id;
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

    public String getUrStatus() {
        return urStatus;
    }

    public void setUrStatus(String urStatus) {
        this.urStatus = urStatus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Long agent_id) {
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
