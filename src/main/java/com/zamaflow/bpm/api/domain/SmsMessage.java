package com.zamaflow.bpm.api.domain;

public class SmsMessage {
	
	private String cellPhoneNumber;
	private String SmsContent;
	
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}
	public String getSmsContent() {
		return SmsContent;
	}
	public void setSmsContent(String smsContent) {
		SmsContent = smsContent;
	}
	
	public SmsMessage(String cellPhoneNumber, String smsContent) {
		super();
		this.cellPhoneNumber = cellPhoneNumber;
		SmsContent = smsContent;
	}
	
}
