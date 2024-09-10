package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int document_id;

    @Column(nullable = false)
    private String document;

    @ManyToOne
    @JoinColumn(name = "policyId", nullable = false)
    private Policy policy;

    public Document() {}

    public Document(String document, Policy policy) {
        this.document = document;
        this.policy = policy;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

// Getters and setters
}
