package com.example.realestate.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "interaction")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private int interactionID;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "interaction_type")
    private String interactionType;

    @Column(name = "interaction_date")
    private LocalDate interactionDate;

    @Column(name = "additional_notes")
    private String additionalNotes;

    public Interaction(int customerID, String interactionType, LocalDate interactionDate, String additionalNotes) {
        this.interactionType = interactionType;
        this.interactionDate = interactionDate;
        this.additionalNotes = additionalNotes;
    }

    public Interaction() {
    }

    // Getters and setters
    public int getInteractionID() {
        return interactionID;
    }

    public void setInteractionID(int interactionID) {
        this.interactionID = interactionID;
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public LocalDate getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDate interactionDate) {
        this.interactionDate = interactionDate;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}