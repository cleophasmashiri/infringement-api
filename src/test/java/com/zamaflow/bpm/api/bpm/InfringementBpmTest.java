package com.zamaflow.bpm.api.bpm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.repository.DriverRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Deployment(resources = {"TrafficFinesProcess.bpmn"})
public class InfringementBpmTest {

    private Driver driver;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RuntimeService runtimeService;

    private static final String TEST_PROCESS_KEY = "trafficProcess";
    private static final String DRIVER_EMAIL = "jim@gmail.com";

    @Before
    public void setup() {
        Driver driver = new Driver().firstName("Jim").lastName("Mike").email(DRIVER_EMAIL).cellNumber("+2773365537314")
            .nationalIdNumber("59C9999961212");
        driver = driverRepository.save(driver);
    }


    @Test
    public void givenTheHasBeenIssuedInfrigementHasOptedToPayThenProcessWillCompleteSuccessfully() {

        Map<String, Object> variables = getVariables();

        ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey(TEST_PROCESS_KEY, variables);

        assertThat(processInstance).isStarted().hasPassed("issueNewInfrigementNotice").isWaitingAt("diverNomination")
            .task().isAssignedTo(DRIVER_EMAIL);

        long time = ClockUtil.getCurrentTime().getTime();
        long seconds = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(time + seconds * 1000));

        Job job = processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(job.getId());

        assertThat(processInstance).hasPassed("sendDriverReminder");

        complete(task(), Variables.createVariables().putValue("driverSelects",
            "Pay"));

        assertThat(processInstance).hasPassed("processPayment").isEnded();
    }

    private Map<String, Object> getVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("driverIdNumber", "59C9999961212");
        variables.put("plateNumber", "RY01GHGP");
        variables.put("infringementType", "OverSpeeding");
        variables.put("infringementNotes", "Over-Speeding");
        variables.put("driverNotes", "Over-Speeding");
        variables.put("adminNotes", "Over-Speeding");
        // driverNotes
        return variables;
    }

    @Test
    public void
    givenTheHasBeenIssuedInfrigementHasOptedMakePresentationAdminAcceptThenProcessWillBeCancelled() {

        Map<String, Object> variables = getVariables();

        ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey(TEST_PROCESS_KEY, variables);

        assertThat(processInstance).isStarted().hasPassed("issueNewInfrigementNotice").isWaitingAt("diverNomination")
            .task().isAssignedTo(DRIVER_EMAIL);

        long time = ClockUtil.getCurrentTime().getTime();
        long seconds = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(time + seconds * 1000));
        Job job =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(job.getId());

        assertThat(processInstance).hasPassed("sendDriverReminder");

        // Nominate Another Driver / Representation / Go To Court
        complete(task(), Variables.createVariables().putValue("driverSelects",
            "Representation"));

        assertThat(processInstance).isWaitingAt("reviewDriverInfrigement").task().hasCandidateGroup("trafficAdmin");

        long timeTrafficAdmin = ClockUtil.getCurrentTime().getTime();
        long secondsTrafficAdmin = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(timeTrafficAdmin + secondsTrafficAdmin *
            1000));
        Job jobTrafficAdmin =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(jobTrafficAdmin.getId());

        assertThat(processInstance).hasPassed("reminderNotifyTrafficAdmin");

        complete(task(), Variables.createVariables().putValue("trafficAdminSelects",
            "Cancel"));

        assertThat(processInstance).hasPassed("cancelInfrigement").isEnded();

    }

    @Test
    public void
    givenTheHasBeenIssuedInfrigementHasOptedMakeAssignAnotherDriverAdminAcceptThenProcessWillBeAssigned() {

        Map<String, Object> variables = getVariables();

        ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey(TEST_PROCESS_KEY, variables);

        assertThat(processInstance).isStarted().hasPassed("issueNewInfrigementNotice").isWaitingAt("diverNomination")
            .task().isAssignedTo(DRIVER_EMAIL);

        long time = ClockUtil.getCurrentTime().getTime();
        long seconds = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(time + seconds * 1000));
        Job job =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(job.getId());

        assertThat(processInstance).hasPassed("sendDriverReminder");

        // Nominate Another Driver / Representation / Go To Court
        complete(task(), Variables.createVariables().putValue("driverSelects",
            "Representation"));

        assertThat(processInstance).isWaitingAt("reviewDriverInfrigement").task().hasCandidateGroup("trafficAdmin");

        long timeTrafficAdmin = ClockUtil.getCurrentTime().getTime();
        long secondsTrafficAdmin = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(timeTrafficAdmin + secondsTrafficAdmin *
            1000));
        Job jobTrafficAdmin =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(jobTrafficAdmin.getId());

        assertThat(processInstance).hasPassed("reminderNotifyTrafficAdmin");

        complete(task(), Variables.createVariables().putValue("trafficAdminSelects",
            "Assign Another Driver"));

        assertThat(processInstance).hasPassed("assignAnotherDriver").isEnded();

    }

    @Test
    public void
    givenTheHasBeenIssuedInfrigementHasOptedGoToCourtAdminAcceptThenProcessWillBeAssigned() {

        Map<String, Object> variables = getVariables();

        ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey(TEST_PROCESS_KEY, variables);

        assertThat(processInstance).isStarted().hasPassed("issueNewInfrigementNotice").isWaitingAt("diverNomination")
            .task().isAssignedTo(DRIVER_EMAIL);

        long time = ClockUtil.getCurrentTime().getTime();
        long seconds = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(time + seconds * 1000));
        Job job =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(job.getId());

        assertThat(processInstance).hasPassed("sendDriverReminder");

        // Nominate Another Driver / Representation / Go To Court
        complete(task(), Variables.createVariables().putValue("driverSelects",
            "Representation"));

        assertThat(processInstance).isWaitingAt("reviewDriverInfrigement").task().hasCandidateGroup("trafficAdmin");

        long timeTrafficAdmin = ClockUtil.getCurrentTime().getTime();
        long secondsTrafficAdmin = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(timeTrafficAdmin + secondsTrafficAdmin *
            1000));
        Job jobTrafficAdmin =
            processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(jobTrafficAdmin.getId());

        assertThat(processInstance).hasPassed("reminderNotifyTrafficAdmin");

        complete(task(), Variables.createVariables().putValue("trafficAdminSelects",
            "Go To Court"));

        assertThat(processInstance).hasPassed("generateCourtDocs").isEnded();

    }
}
