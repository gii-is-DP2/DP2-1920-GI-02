
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;


	// findByLastName(@Param("lastName") String lastName) POSITIVE TEST
	@Test
	void shouldFindOwnerByLastName() {
		//1. Arrange
		String lastName = "Davis";
		//2. Act
		Collection<Owner> owners = this.ownerRepository.findByLastName(lastName);
		//3. Assert
		assertThat(owners).hasSize(2);
	}

	// findByLastName(@Param("lastName") String lastName) NEGATIVE TEST
	@Test
	void shouldNotFindOwnerByLastName() {
		//1. Arrange
		String lastName = "FALSELASTNAME";
		//2. Act
		Collection<Owner> owners = this.ownerRepository.findByLastName(lastName);
		//3. Assert
		assertThat(owners).hasSize(0);
	}

	// findById(@Param("id") int id) POSITIVE TEST
	@Test
	void shouldFindOwnerById() {
		//1. Arrange
		int id = 1;
		//2. Act
		Owner owner = this.ownerRepository.findById(id);
		//3. Assert
		assertThat(owner).isNotNull();
	}

	// findById(@Param("id") int id) NEGATIVE TEST
	@Test
	void shouldNotFindOwnerById() {
		//1. Arrange
		int id = 995;
		//2. Act
		Owner owner = this.ownerRepository.findById(id);
		//3. Assert
		assertThat(owner).isNull();
	}

	// findByUsername(@Param("username") String username) POSITIVE TEST
	@Test
	void shouldFindOwnerByUsername() {
		//1. Arrange
		String username = "gfranklin";
		//2. Act
		Owner owner = this.ownerRepository.findByUsername(username);
		//3. Assert
		assertThat(owner).isNotNull();
	}

	//  findByUsername(@Param("username") String username) NEGATIVE TEST
	@Test
	void shouldNotFindOwnerByUsername() {
		//1. Arrange
		String username = "FALSEUSERNAME";
		//2. Act
		Owner owner = this.ownerRepository.findByUsername(username);
		//3. Assert
		assertThat(owner).isNull();
	}

}
