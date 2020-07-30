package com.zamaflow.bpm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Infringement.
 */
@Entity
@Table(name = "infringement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Infringement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "infringement_type")
    private String infringementType;

    @Column(name = "date_done")
    private Instant dateDone;

    @Column(name = "done_by")
    private String doneBy;

    @OneToMany(mappedBy = "infringement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InfringementAction> infringementActions = new HashSet<>();

    @OneToMany(mappedBy = "infringement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "infringements", allowSetters = true)
    private Driver driver;

    @ManyToOne
    @JsonIgnoreProperties(value = "infringements", allowSetters = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Infringement processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getInfringementType() {
        return infringementType;
    }

    public Infringement infringementType(String infringementType) {
        this.infringementType = infringementType;
        return this;
    }

    public void setInfringementType(String infringementType) {
        this.infringementType = infringementType;
    }

    public Instant getDateDone() {
        return dateDone;
    }

    public Infringement dateDone(Instant dateDone) {
        this.dateDone = dateDone;
        return this;
    }

    public void setDateDone(Instant dateDone) {
        this.dateDone = dateDone;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public Infringement doneBy(String doneBy) {
        this.doneBy = doneBy;
        return this;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public Set<InfringementAction> getInfringementActions() {
        return infringementActions;
    }

    public Infringement infringementActions(Set<InfringementAction> infringementActions) {
        this.infringementActions = infringementActions;
        return this;
    }

    public Infringement addInfringementAction(InfringementAction infringementAction) {
        this.infringementActions.add(infringementAction);
        infringementAction.setInfringement(this);
        return this;
    }

    public Infringement removeInfringementAction(InfringementAction infringementAction) {
        this.infringementActions.remove(infringementAction);
        infringementAction.setInfringement(null);
        return this;
    }

    public void setInfringementActions(Set<InfringementAction> infringementActions) {
        this.infringementActions = infringementActions;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Infringement documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Infringement addDocument(Document document) {
        this.documents.add(document);
        document.setInfringement(this);
        return this;
    }

    public Infringement removeDocument(Document document) {
        this.documents.remove(document);
        document.setInfringement(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Driver getDriver() {
        return driver;
    }

    public Infringement driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Infringement vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Infringement)) {
            return false;
        }
        return id != null && id.equals(((Infringement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Infringement{" +
            "id=" + getId() +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", infringementType='" + getInfringementType() + "'" +
            ", dateDone='" + getDateDone() + "'" +
            ", doneBy='" + getDoneBy() + "'" +
            "}";
    }
}
