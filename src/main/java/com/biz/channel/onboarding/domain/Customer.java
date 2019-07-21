package com.biz.channel.onboarding.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "is_agree_terms")
    private Boolean isAgreeTerms;

    @Column(name = "is_agree_policy")
    private Boolean isAgreePolicy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "activated_date")
    private Instant activatedDate;

    @Column(name = "status")
    private Boolean status;

    @OneToOne
    @JoinColumn(unique = true)
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsAgreeTerms() {
        return isAgreeTerms;
    }

    public Customer isAgreeTerms(Boolean isAgreeTerms) {
        this.isAgreeTerms = isAgreeTerms;
        return this;
    }

    public void setIsAgreeTerms(Boolean isAgreeTerms) {
        this.isAgreeTerms = isAgreeTerms;
    }

    public Boolean isIsAgreePolicy() {
        return isAgreePolicy;
    }

    public Customer isAgreePolicy(Boolean isAgreePolicy) {
        this.isAgreePolicy = isAgreePolicy;
        return this;
    }

    public void setIsAgreePolicy(Boolean isAgreePolicy) {
        this.isAgreePolicy = isAgreePolicy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Customer createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Customer updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getActivatedDate() {
        return activatedDate;
    }

    public Customer activatedDate(Instant activatedDate) {
        this.activatedDate = activatedDate;
        return this;
    }

    public void setActivatedDate(Instant activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Boolean isStatus() {
        return status;
    }

    public Customer status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Country getCountry() {
        return country;
    }

    public Customer country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", password='" + getPassword() + "'" +
            ", isAgreeTerms='" + isIsAgreeTerms() + "'" +
            ", isAgreePolicy='" + isIsAgreePolicy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", activatedDate='" + getActivatedDate() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
