package com.zamaflow.bpm.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "national_id_number")
    private String nationalIdNumber;

    @Column(name = "cell_number")
    private String cellNumber;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_property_number")
    private String streetPropertyNumber;

    @Column(name = "unit_number")
    private String unitNumber;

    @OneToMany(mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vehicle> vehicles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Driver middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public Driver email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public Driver nationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
        return this;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Driver cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getProvince() {
        return province;
    }

    public Driver province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public Driver city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public Driver suburb(String suburb) {
        this.suburb = suburb;
        return this;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getStreetName() {
        return streetName;
    }

    public Driver streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetPropertyNumber() {
        return streetPropertyNumber;
    }

    public Driver streetPropertyNumber(String streetPropertyNumber) {
        this.streetPropertyNumber = streetPropertyNumber;
        return this;
    }

    public void setStreetPropertyNumber(String streetPropertyNumber) {
        this.streetPropertyNumber = streetPropertyNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public Driver unitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public Driver vehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Driver addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setDriver(this);
        return this;
    }

    public Driver removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setDriver(null);
        return this;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", email='" + getEmail() + "'" +
            ", nationalIdNumber='" + getNationalIdNumber() + "'" +
            ", cellNumber='" + getCellNumber() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", suburb='" + getSuburb() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", streetPropertyNumber='" + getStreetPropertyNumber() + "'" +
            ", unitNumber='" + getUnitNumber() + "'" +
            "}";
    }
}
