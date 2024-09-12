package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)  // Use inheritance for User subclasses
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
//    @SequenceGenerator(
//            name = "users_sequence",
//            sequenceName = "users_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "users_sequence"
//    )
//    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate date_of_birth;

    @Column
    private int loyalty;

    @Column
    private String dl_num;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate  dl_issue_date;

    @Temporal(TemporalType.DATE)
    @Column
    private LocalDate  dl_expire_Date;

    @Column
    private String bank_account;

    @Column
    private String role;

//    @OneToMany(mappedBy = "users")
//    private List<Policy> policies;
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy> policies;

    public Users() {}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users(String username, String password, String name, String email, String phone, String address, LocalDate date_of_birth, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.role = role;
    }

    public Users(Long id, String username, String password, String name, String email, String phone, String address, LocalDate date_of_birth, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.role = role;
    }

    public Users(Long id, String username, String password, String name, String email, String phone, String address, LocalDate date_of_birth, int loyalty, String dl_num, LocalDate dl_issue_date, LocalDate dl_expire_Date, String bank_account, String role, List<Policy> policies) {
        this.id = id;
        this.username = username;
        this.password = password;
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
        this.role = role;
        this.policies = policies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
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

    public LocalDate getDl_issue_date() {
        return dl_issue_date;
    }

    public void setDl_issue_date(LocalDate dl_issue_date) {
        this.dl_issue_date = dl_issue_date;
    }

    public LocalDate getDl_expire_Date() {
        return dl_expire_Date;
    }

    public void setDl_expire_Date(LocalDate dl_expire_Date) {
        this.dl_expire_Date = dl_expire_Date;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
