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

    @Column(name = "offerType")
    private String offerType;

    @Column(name = "offerStatus")
    private String offerStatus;

    @Column(name = "presentationDate")
    private LocalDate presentationDate;

    @Column(name = "additionalNotes")
    private String additionalNotes;

    @Column(name = "pdfPath")
    private String pdfPath;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "propertyID", nullable = false)
    private Property property;

    public Agreement() {
    }

    public Agreement(Customer customer, Property property, String offerType, String offerStatus,
                     LocalDate presentationDate, String additionalNotes, String pdfPath) {
        this.customer = customer;
        this.property = property;
        this.offerType = offerType;
        this.offerStatus = offerStatus;
        this.presentationDate = presentationDate;
        this.additionalNotes = additionalNotes;
        this.pdfPath = pdfPath;
    }

    // Getters and setters
    public int getDisplayID() {
        return displayID;
    }

    public void setDisplayID(int displayID) {
        this.displayID = displayID;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
