package com.zamaflow.bpm.api.service;

import java.util.Arrays;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import com.zamaflow.bpm.api.domain.InvoiceBase;
import com.zamaflow.bpm.api.domain.LineItem;
import com.zamaflow.bpm.api.domain.Quotation;

@Component
public class InvoiceService {
//	
//	 public InvoiceBase generateNew(String addressline1, String addressCity, String addressState, String addressPostalCode, String customerName) {
//	        InvoiceBase quotation = new Quotation()
//	        		.addressline1(addressline1)
//	        		.addressCity(addressCity)
//	        		.customerName(customerName)
//	        		.lineItems(Arrays.asList(new LineItem("Infringement 1", 1, 1000.0), new LineItem("Infringement 2", 1, 3000.0)));
//	    
//	       return quotation;
//	    }

}
