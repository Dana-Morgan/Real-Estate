package com.example.realestate.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    private int customerId;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "customerEmail")
    private String customerEmail;

    @Column(name = "customerAddDate")
    private Date addDate;

    @Column(name = "customerAdditionNote")
    private String additionNote;

    @Column(name = "customerPhoneNumber")
    private String customerPhoneNumber;

    @Column(name = "customerActivityStatus")
    private String customerActivityStatus;

    @Column(name = "customerPrefernces")
    private String customerPrefernces;




    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getAddDate() {
        return addDate;
    }
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAdditionNote() {
        return additionNote;
    }
    public void setAdditionNote(String additionNote) {
        this.additionNote = additionNote;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerActivityStatus() {
        return customerActivityStatus;
    }
    public void setCustomerActivityStatus(String customerActivityStatus) {
        this.customerActivityStatus = customerActivityStatus;
    }

    public String getCustomerPrefernces() {
        return customerPrefernces;
    }
    public void setCustomerPrefernces(String customerPrefernces) {
        this.customerPrefernces = customerPrefernces;
    }
}