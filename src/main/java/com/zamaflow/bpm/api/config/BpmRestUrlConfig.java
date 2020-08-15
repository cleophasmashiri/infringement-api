package com.zamaflow.bpm.api.config;

import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api/engine-rest")
public class BpmRestUrlConfig extends CamundaJerseyResourceConfig {
}
