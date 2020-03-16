
package org.group2.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "visit_types")
public class VisitType extends NamedEntity {

	// ATTRIBUTES -------------------------------------------------------------

	/**
	 * default duration of a visit of the type, in minutes
	 */
	@NotNull
	private Integer	duration;

	/**
	 * default price of a visit of the type, in euros and cents
	 */
	@NotNull
	private Double	price;

	// RELATIONSHIPS ----------------------------------------------------------

	// GETTERS / SETTERS ------------------------------------------------------

}
