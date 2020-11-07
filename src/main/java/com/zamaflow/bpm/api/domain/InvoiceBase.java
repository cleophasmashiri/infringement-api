package com.zamaflow.bpm.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.bytebuddy.asm.Advice.Return;

public abstract class InvoiceBase implements Serializable {

    private LineItem lineItem;
    private List<LineItem> lineItems = new ArrayList<>();

    private String customerName;
    private String addressline1;
    private String addressCity;
    private String addressState;
    private String addressPostalCode;

    public double getTotalAmount() {
        return lineItems.stream().mapToDouble(f -> f.getAmount()).sum();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
    

    public InvoiceBase lineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public InvoiceBase customerName(String customerName) {
    	this.customerName = customerName;
    	return this;
    } 

    public String getAddressline1() {
        return addressline1;
    }

    public InvoiceBase addressline1(String addressline1) {
    	this.addressline1 = addressline1;
    	return this;
    } 

    public String getAddressCity() {
        return addressCity;
    }
    
    public InvoiceBase addressCity(String addressCity) {
    	this.addressCity = addressCity;
    	return this;
    } 

    public String getAddressState() {
        return addressState;
    }

    public InvoiceBase addressState(String addressState) {
    	this.addressState = addressState;
    	return this;
    } 

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public InvoiceBase addressPostalCode(String addressPostalCode) {
    	this.addressPostalCode = addressPostalCode;
    	return this;
    }

    public void addLineItem(String description, int quantity, double unitPrice) {
        lineItems.add(new LineItem(description, quantity, unitPrice));
    }
}