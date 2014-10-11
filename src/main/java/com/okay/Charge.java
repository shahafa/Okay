package com.okay;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Date;

public class Charge {
    private int id;
    private int accountNumber;
    private double purchaseAmount;
    private int numberOfPayments;
    private Date purchaseDateTime;
    private String businessName;
    private String businessLocation;
    private String category;

    public Charge() { }

    public Charge (DBObject chargeObject) {
        deserialize(chargeObject);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public void setNumberOfPayments(int numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public Date getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(Date purchaseDate) {
        this.purchaseDateTime = purchaseDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DBObject serialize() {

        return new BasicDBObject()
                .append("id", getId())
                .append("account_number", getAccountNumber())
                .append("purchase_amount", getPurchaseAmount())
                .append("number_of_payments", getNumberOfPayments())
                .append("purchase_date_time", getPurchaseDateTime())
                .append("business_name", getBusinessName())
                .append("business_location", getBusinessLocation())
                .append("category", getCategory());
    }


    public void deserialize(DBObject chargeObject)
    {
        setId(Integer.parseInt(chargeObject.get("id").toString()));
        setAccountNumber(Integer.parseInt(chargeObject.get("account_number").toString()));
        setPurchaseAmount(Double.parseDouble(chargeObject.get("purchase_amount").toString()));
        setNumberOfPayments(Integer.parseInt(chargeObject.get("number_of_payments").toString()));
        setBusinessName(chargeObject.get("business_name").toString());
        setBusinessLocation(chargeObject.get("business_location").toString());
        setCategory(chargeObject.get("category").toString());
        setPurchaseDateTime((Date)chargeObject.get("purchase_date_time"));
    }
}
