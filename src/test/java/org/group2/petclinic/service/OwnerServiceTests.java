
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class OwnerServiceTests {

	@Autowired
	protected OwnerService ownerService;


	@Test
	@Transactional
	void shouldSaveNewOwner() {
		//1. Arrange
		User user = new User();
		user.setUsername("jgarcia");
		user.setPassword("jgarcia");
		user.setEnabled(true);

		Owner owner = new Owner();
		owner.setFirstName("Juan");
		owner.setLastName("García");
		owner.setUser(user);
		owner.setAddress("Calle Larga 1");
		owner.setCity("Sevilla");
		owner.setTelephone("954123123");
		//2. Act
		ownerService.saveOwner(owner);
		//3. Assert
		Owner retrieved = ownerService.findOwnerByUsername("jgarcia");
		assertThat(retrieved).isEqualTo(owner);
	}

	@Test
	void shouldFindOwnerById() {
		//1. Arrange
		//2. Act
		Owner owner = ownerService.findOwnerById(1);
		//3. Assert
		assertThat(owner.getFirstName()).isEqualTo("George");
		assertThat(owner.getLastName()).isEqualTo("Franklin");
	}

	@Test
	void shouldFindOwnerByUsername() {
		//1. Arrange
		//2. Act
		Owner owner = ownerService.findOwnerByUsername("gfranklin");
		//3. Assert
		assertThat(owner.getFirstName()).isEqualTo("George");
		assertThat(owner.getLastName()).isEqualTo("Franklin");
	}

	@Test
	void shouldFindOwnersByLastName() {
		//1. Arrange
		//2. Act
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Davis");
		//3. Assert
		assertThat(owners.size()).isEqualTo(2);

		owners = this.ownerService.findOwnerByLastName("Daviss");
		assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	void shouldFindNoOwnersByLastName() {
		//1. Arrange
		//2. Act
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Daviss");
		//3. Assert
		assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.ownerService.findOwnerById(1);
		assertThat(owner.getLastName()).startsWith("Franklin");
		assertThat(owner.getPets().size()).isEqualTo(1);
		assertThat(owner.getPets().get(0).getType()).isNotNull();
		assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
	}

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		owner.setAddress("4, Evans Street");
		owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
		User user = new User();
		user.setUsername("Sam");
		user.setPassword("supersecretpassword");
		user.setEnabled(true);
		owner.setUser(user);

		this.ownerService.saveOwner(owner);
		assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.ownerService.findOwnerByLastName("Schultz");
		assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Owner owner = this.ownerService.findOwnerById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.ownerService.saveOwner(owner);

		// retrieving new name from database
		owner = this.ownerService.findOwnerById(1);
		assertThat(owner.getLastName()).isEqualTo(newLastName);
	}

}
