
package org.group2.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 * @author Miguel Macarro
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

	// CONSTRUCTOR ------------------------------------------------------------

	/**
	 * Creates a new instance of Visit for the current date
	 */
	public Visit() {
		//this.moment = LocalDate.now();
	}


	// ATTRIBUTES -------------------------------------------------------------

	/**
	 * Holds value of property moment.
	 */
	@Column(name = "visit_moment")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	private LocalDateTime	moment;

	/**
	 * Holds value of property description.
	 */
	@NotEmpty
	@Column(name = "description")
	private String			description;

	// RELATIONSHIPS ----------------------------------------------------------

	/**
	 * Holds value of property pet.
	 */
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet				pet;

	// GETTERS / SETTERS ------------------------------------------------------

}
