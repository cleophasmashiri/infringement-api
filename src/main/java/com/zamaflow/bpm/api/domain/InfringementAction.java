package com.zamaflow.bpm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;

/**
 * A InfringementAction.
 */
@Entity
@Table(name = "infringement_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InfringementAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "infringement_action_type")
    private InfringementActionType infringementActionType;

    @Column(name = "date_done")
    private Instant dateDone;

    @Column(name = "done_by")
    private String doneBy;

    @Column(name = "amount")
    private double amount;

    @Column(name = "points")
    private int points;

    @ManyToOne
    @JsonIgnoreProperties(value = "infringementActions", allowSetters = true)
    private Infringement infringement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public InfringementAction points(int points) {
        this.points = points;
        return this;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getAmount() {
        return amount;
    }

    public InfringementAction amount(double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InfringementAction processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public String getNotes() {
        return notes;
    }

    public InfringementAction notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InfringementActionType getInfringementActionType() {
        return infringementActionType;
    }

    public InfringementAction infringementActionType(InfringementActionType infringementActionType) {
        this.infringementActionType = infringementActionType;
        return this;
    }

    public void setInfringementActionType(InfringementActionType infringementActionType) {
        this.infringementActionType = infringementActionType;
    }

    public Instant getDateDone() {
        return dateDone;
    }

    public InfringementAction dateDone(Instant dateDone) {
        this.dateDone = dateDone;
        return this;
    }

    public void setDateDone(Instant dateDone) {
        this.dateDone = dateDone;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public InfringementAction doneBy(String doneBy) {
        this.doneBy = doneBy;
        return this;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public Infringement getInfringement() {
        return infringement;
    }

    public InfringementAction infringement(Infringement infringement) {
        this.infringement = infringement;
        return this;
    }

    public void setInfringement(Infringement infringement) {
        this.infringement = infringement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfringementAction)) {
            return false;
        }
        return id != null && id.equals(((InfringementAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfringementAction{" +
            "id=" + getId() +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", notes='" + getNotes() + "'" +
            ", infringementActionType='" + getInfringementActionType() + "'" +
            ", dateDone='" + getDateDone() + "'" +
            ", doneBy='" + getDoneBy() + "'" +
            "}";
    }
}
