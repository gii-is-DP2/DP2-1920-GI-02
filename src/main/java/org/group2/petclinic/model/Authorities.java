
package org.group2.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {

	// ATTRIBUTES -------------------------------------------------------------

	@Id
	String	username;
	String	authority;

	// RELATIONSHIPS ----------------------------------------------------------

	// GETTERS / SETTERS ------------------------------------------------------

}
