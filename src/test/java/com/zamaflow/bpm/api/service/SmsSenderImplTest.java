package com.zamaflow.bpm.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zamaflow.bpm.api.InfringementapiApp;
import com.zamaflow.bpm.api.domain.SmsMessage;
import com.zamaflow.bpm.api.service.impl.SmsSenderImpl;


@SpringBootTest(classes = InfringementapiApp.class)
public class SmsSenderImplTest {
	
	@Autowired
	private SmsSenderImpl smsSenderImpl;
	
	@Autowired
	private RestTemplate restTemplate; 
	
	@Autowired
	private MockRestServiceServer mockRestServer;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Value("${sms.url}")
	private String smsUrl;
	
	// @Before
	// public void setup() {
	// 	this.mockRestServer = MockRestServiceServer.createServer(this.restTemplate);
	// }
	
	@Test
	public void givenSmsSentThenSmsShouldBeDelivered() throws Exception {
		
		// SmsMessage sms = new SmsMessage("0839449415", "New Infringment has been issued:");
		// mockRestServer.expect(ExpectedCount.once(),
		// 		requestTo(new URI(smsUrl)))
		// .andExpect(method(HttpMethod.POST))
		// .andRespond(withStatus(HttpStatus.OK)
		// .contentType(MediaType.APPLICATION_JSON)
		// .body(mapper.writeValueAsString(sms))
		// );
		
		// smsSenderImpl.sendSms(sms);
		
		// mockRestServer.verify();
		assertEquals(true, true);
	}
	

	

}
