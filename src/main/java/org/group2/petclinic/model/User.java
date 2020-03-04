
package org.group2.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	// ATTRIBUTES -------------------------------------------------------------

	@Id
	String	username;

	String	password;

	boolean	enabled;

	// RELATIONSHIPS ----------------------------------------------------------

	// GETTERS / SETTERS ------------------------------------------------------

}
