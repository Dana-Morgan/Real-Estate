package com.example.realestate.models;
import java.time.LocalDate;

public class Interaction {

    private String interactionID;
    private String customerID;
    private String interactionType;
    private LocalDate interactionDate;
    private String additionalNotes;

    public Interaction() {
    }

    public Interaction(String interactionID, String customerID, String interactionType, LocalDate interactionDate, String additionalNotes) {
        this.interactionID = interactionID;
        this.customerID = customerID;
        this.interactionType = interactionType;
        this.interactionDate = interactionDate;
        this.additionalNotes = additionalNotes;
    }
    public String getInteractionID() {
        return interactionID;
    }

    public void setInteractionID(String interactionID) {
        this.interactionID = interactionID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
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
