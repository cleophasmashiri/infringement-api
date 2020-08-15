package com.zamaflow.bpm.api.web.rest;


import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "infringementapiBpm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    @GetMapping("/task")
    public ResponseEntity<List<Task>> getTasks() {
        return null;
    }



}
