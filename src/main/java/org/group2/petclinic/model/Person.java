
package org.group2.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

/**
 * JavaBean domain object representing an person. Should be used as an abstract class
 *
 * @author Ken Krebs
 * @author Miguel Macarro
 */
@MappedSuperclass
public class Person extends BaseEntity {

	// ATTRIBUTES -------------------------------------------------------------

	@Column(name = "first_name")
	@NotEmpty
	protected String	firstName;

	@Column(name = "last_name")
	@NotEmpty
	protected String	lastName;

	// RELATIONSHIPS ----------------------------------------------------------

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User		user;


	// GETTERS / SETTERS ------------------------------------------------------

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
