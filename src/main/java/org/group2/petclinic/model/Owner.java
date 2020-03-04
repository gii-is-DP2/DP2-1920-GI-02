
package org.group2.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Miguel Macarro
 */
@Entity
@Table(name = "owners")
public class Owner extends Person {

	// ATTRIBUTES -------------------------------------------------------------

	@Column(name = "address")
	@NotEmpty
	private String		address;

	@Column(name = "city")
	@NotEmpty
	private String		city;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String		telephone;

	// RELATIONSHIPS ----------------------------------------------------------

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Pet>	pets;

	//TODO delete:
	//
	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "username", referencedColumnName = "username")
	//private User		user;
	//

	// GETTERS / SETTERS ------------------------------------------------------


	public String getAddress() {
		return this.address;
	}

	//TODO delete:
	//	public User getUser() {
	//		return this.user;
	//	}
	//
	//	public void setUser(final User user) {
	//		this.user = user;
	//	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	protected Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<>();
		}
		return this.pets;
	}

	protected void setPetsInternal(final Set<Pet> pets) {
		this.pets = pets;
	}

	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<>(this.getPetsInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);
	}

	public void addPet(final Pet pet) {
		this.getPetsInternal().add(pet);
		pet.setOwner(this);
	}

	public boolean removePet(final Pet pet) {
		return this.getPetsInternal().remove(pet);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name
	 *            to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(final String name) {
		return this.getPet(name, false);
	}

	public Pet getPetwithIdDifferent(String name, final Integer id) {
		name = name.toLowerCase();
		for (Pet pet : this.getPetsInternal()) {
			String compName = pet.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && pet.getId() != id) {
				return pet;
			}
		}
		return null;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name
	 *            to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name, final boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : this.getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

			.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName()).append("firstName", this.getFirstName()).append("address", this.address).append("city", this.city).append("telephone", this.telephone).toString();
	}

}
