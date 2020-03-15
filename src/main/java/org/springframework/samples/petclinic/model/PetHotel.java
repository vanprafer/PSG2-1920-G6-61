package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pet_hotels")
public class PetHotel extends BaseEntity{
	
	@Column(name = "description")
	@NotEmpty
	private String description;
	
	@Column(name = "start")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate start;
	
	@Column(name = "finish")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate finish;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getFinish() {
		return finish;
	}

	public Pet getPet() {
		return pet;
	}


}
