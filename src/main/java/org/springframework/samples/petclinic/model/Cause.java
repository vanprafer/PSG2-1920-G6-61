package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "causes")
public class Cause extends NamedEntity {

    @Column(name = "description")
	@NotEmpty
    private String description;

    @Column(name = "budget_target")
	@NotEmpty
    private Double budgetTarget;

    @Column(name = "organization")
	@NotEmpty
	private String organization;

    @Column(name = "is_open")
	@NotNull
	private Boolean isOpen;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

    public String getDescription() {
		return this.description;
	}

    public Double getBudgetTarget() {
		return this.budgetTarget;
	}

    public String getOrganization() {
		return this.organization;
	}

    public Boolean getIsOpen() {
        return this.isOpen; 
    }

    public Owner getOwner() {
		return this.owner;
	}

    public void setDescription(String description) {
		this.description = description;
	}

    public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

    public void setOrganization(String organization) {
		this.organization = organization;
	}

    public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

    protected void setOwner(Owner owner) {
		this.owner = owner;
	}
}