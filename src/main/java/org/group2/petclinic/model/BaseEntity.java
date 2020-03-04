
package org.group2.petclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects
 * needing this property.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
@MappedSuperclass
public class BaseEntity {

	// ATTRIBUTES -------------------------------------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	// RELATIONSHIPS ----------------------------------------------------------


	// GETTERS / SETTERS ------------------------------------------------------

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public boolean isNew() {
		return this.id == null;
	}

}
