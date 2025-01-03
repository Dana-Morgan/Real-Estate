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

    @Column(name = "customer_id")
    private int customerID;

    @Column(name = "interaction_type")
    private String interactionType;

    @Column(name = "interaction_date")
    private LocalDate interactionDate;

    @Column(name = "additional_notes")
    private String additionalNotes;

    public Interaction(int customerID, String interactionType, LocalDate interactionDate, String additionalNotes) {
        this.customerID = customerID;
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

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
