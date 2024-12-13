package com.example.realestate.models;

import java.time.LocalDate;

public class Agreement {

    private String displayID;
    private String customerID;
    private String propertyID;
    private String offerType;
    private String offerStatus;
    private LocalDate presentationDate;
    private String additionalNotes;

    public Agreement() {
    }

    public Agreement(String displayID, String customerID, String propertyID, String offerType, String offerStatus, LocalDate presentationDate, String additionalNotes) {
        this.displayID = displayID;
        this.customerID = customerID;
        this.propertyID = propertyID;
        this.offerType = offerType;
        this.offerStatus = offerStatus;
        this.presentationDate = presentationDate;
        this.additionalNotes = additionalNotes;
    }

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public LocalDate getPresentationDate() {
        return presentationDate;
    }

    public void setPresentationDate(LocalDate presentationDate) {
        this.presentationDate = presentationDate;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
