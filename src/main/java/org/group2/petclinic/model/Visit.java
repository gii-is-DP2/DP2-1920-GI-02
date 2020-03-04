
package org.group2.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 */
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

	// CONSTRUCTOR ------------------------------------------------------------

	/**
	 * Creates a new instance of Visit for the current date
	 */
	public Visit() {
		this.date = LocalDate.now();
	}


	// ATTRIBUTES -------------------------------------------------------------

	/**
	 * Holds value of property date.
	 */
	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	/**
	 * Holds value of property description.
	 */
	@NotEmpty
	@Column(name = "description")
	private String		description;

	// RELATIONSHIPS ----------------------------------------------------------

	/**
	 * Holds value of property pet.
	 */
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet			pet;


	// GETTERS / SETTERS ------------------------------------------------------

	/**
	 * Getter for property date.
	 *
	 * @return Value of property date.
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * Setter for property date.
	 *
	 * @param date
	 *            New value of property date.
	 */
	public void setDate(final LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter for property description.
	 *
	 * @return Value of property description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter for property description.
	 *
	 * @param description
	 *            New value of property description.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Getter for property pet.
	 *
	 * @return Value of property pet.
	 */
	public Pet getPet() {
		return this.pet;
	}

	/**
	 * Setter for property pet.
	 *
	 * @param pet
	 *            New value of property pet.
	 */
	public void setPet(final Pet pet) {
		this.pet = pet;
	}

}
