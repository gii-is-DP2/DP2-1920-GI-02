
package org.group2.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

	public Visit() {

	}


	// ATTRIBUTES -------------------------------------------------------------

	@Column(name = "visit_date")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@NotEmpty
	@Column(name = "description")
	private String		description;

	// RELATIONSHIPS ----------------------------------------------------------

	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet			pet;

	@ManyToOne(optional = true)
	@JoinColumn(name = "vet_id")
	private Vet			vet;

	@ManyToOne(optional = true)
	@JoinColumn(name = "visit_type_id")
	private VisitType	visitType;

	// GETTERS / SETTERS ------------------------------------------------------

}
