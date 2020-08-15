package com.zamaflow.bpm.api.domain;

import java.io.Serializable;

public class Notification implements Serializable {
	
	 /**
	*
	*/
	private static final long serialVersionUID = 1L;
	
	private String toEmail;
	    private String toFrom;
	    private String subject;
	    private String body;
	    private String action;
	    private String actionDescription;

	    public String getToEmail() {
	        return toEmail;
	    }

	    public Notification setToEmail(String toEmail) {
			this.toEmail = toEmail;
			return this;
	    }

	    public String getToFrom() {
	        return toFrom;
	    }

	    public Notification setToFrom(String toFrom) {
			this.toFrom = toFrom;
			return this;
	    }

	    public String getSubject() {
	        return subject;
	    }

	    public Notification setSubject(String subject) {
			this.subject = subject;
			return this;
	    }

	    public String getBody() {
	        return body;
	    }

	    public Notification setBody(String body) {
			this.body = body;
			return this;
	    }

	    public String getAction() {
	        return action;
	    }

	    public Notification setAction(String action) {
			this.action = action;
			return this;
	    }

	    public String getActionDescription() {
	        return actionDescription;
	    }

	    public Notification setActionDescription(String actionDescription) {
			this.actionDescription = actionDescription;
			return this;
	    }

}
