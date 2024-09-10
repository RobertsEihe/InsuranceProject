package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

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
    private Date dateOfBirth;

    @Column(nullable = false)
    private int loyalty;

    @Column(nullable = false)
    private String dlNum;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dlIssueDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dlExpireDate;

    @Column(nullable = false)
    private String bankAccount;

    @OneToMany(mappedBy = "customer")
    private List<Policy> policies;

    public Customer() {}

    public Customer(String name, String email, String phone, String address, Date dateOfBirth, int loyalty,
                    String dlNum, Date dlIssueDate, Date dlExpireDate, String bankAccount) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.loyalty = loyalty;
        this.dlNum = dlNum;
        this.dlIssueDate = dlIssueDate;
        this.dlExpireDate = dlExpireDate;
        this.bankAccount = bankAccount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    public String getDlNum() {
        return dlNum;
    }

    public void setDlNum(String dlNum) {
        this.dlNum = dlNum;
    }

    public Date getDlIssueDate() {
        return dlIssueDate;
    }

    public void setDlIssueDate(Date dlIssueDate) {
        this.dlIssueDate = dlIssueDate;
    }

    public Date getDlExpireDate() {
        return dlExpireDate;
    }

    public void setDlExpireDate(Date dlExpireDate) {
        this.dlExpireDate = dlExpireDate;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
// Getters and setters
}
