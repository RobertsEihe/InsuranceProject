package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date_of_birth;

    @Column(nullable = false)
    private int loyalty;

    @Column(nullable = false)
    private String dl_num;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dl_issue_date;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dl_expire_Date;

    @Column(nullable = false)
    private String bank_account;

    @OneToMany(mappedBy = "customer")
    private List<Policy> policies;

    public Customer() {}

    public Customer(String name, String email, String phone, String address, Date date_of_birth, int loyalty,
                    String dl_num, Date dl_issue_date, Date dl_expire_Date, String bank_account) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.loyalty = loyalty;
        this.dl_num = dl_num;
        this.dl_issue_date = dl_issue_date;
        this.dl_expire_Date = dl_expire_Date;
        this.bank_account = bank_account;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    public String getDl_num() {
        return dl_num;
    }

    public void setDl_num(String dl_num) {
        this.dl_num = dl_num;
    }

    public Date getDl_issue_date() {
        return dl_issue_date;
    }

    public void setDl_issue_date(Date dl_issue_date) {
        this.dl_issue_date = dl_issue_date;
    }

    public Date getDl_expire_Date() {
        return dl_expire_Date;
    }

    public void setDl_expire_Date(Date dl_expire_Date) {
        this.dl_expire_Date = dl_expire_Date;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
// Getters and setters
}
