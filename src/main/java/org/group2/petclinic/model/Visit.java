
package org.group2.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

	// CONSTRUCTOR ------------------------------------------------------------

	public Visit() {

	}


	// ATTRIBUTES -------------------------------------------------------------

	@Column(name = "visit_moment")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime	moment;

	@NotEmpty
	@Column(name = "description")
	private String			description;

	// RELATIONSHIPS ----------------------------------------------------------

	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet				pet;

	@ManyToOne(optional = true)
	@JoinColumn(name = "vet_id")
	private Vet				vet;

	@ManyToOne(optional = true)
	@JoinColumn(name = "visit_type_id")
	private VisitType		visitType;

	@OneToOne(optional = true)
	@JoinColumn(name = "payment_id")
	private Payment			payment;

	// GETTERS / SETTERS ------------------------------------------------------

}
