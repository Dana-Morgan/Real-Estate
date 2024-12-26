package com.example.realestate.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agreement")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "displayID")
    private int displayID;


    @Column(name = "customerID")
    private int customerID;

    @Column(name = "propertyID")
    private int propertyID;

    @Column(name = "offerType")
    private String offerType;

    @Column(name = "offerStatus")
    private String offerStatus;

    @Column(name = "presentationDate")
    private LocalDate presentationDate;

    @Column(name = "additionalNotes")
    private String additionalNotes;

    public Agreement() {
    }

    public Agreement( int customerID, int propertyID, String offerType, String offerStatus, LocalDate presentationDate, String additionalNotes) {
        this.customerID = customerID;
        this.propertyID = propertyID;
        this.offerType = offerType;
        this.offerStatus = offerStatus;
        this.presentationDate = presentationDate;
        this.additionalNotes = additionalNotes;
    }

    public int getDisplayID() {
        return displayID;
    }

    public void setDisplayID(int displayID) {
        this.displayID = displayID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
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
