
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.User;
import org.group2.petclinic.repository.OwnerRepository;
import org.group2.petclinic.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTests {

	@Mock
	private OwnerRepository	stubOwnerRepository;

	protected OwnerService	ownerService;

	// saveOwner(final Owner owner) NOT TESTED (implemented by springframework)


	// findOwnerById(final int id) POSITIVE TEST
	@Test
	void shouldFindOwnerById() {
		//1. Arrange
		Owner toReturn = new Owner();
		toReturn.setId(1);
		toReturn.setFirstName("Juan");
		toReturn.setLastName("García");
		toReturn.setUser(mock(User.class));
		toReturn.setAddress("Calle Larga 1");
		toReturn.setCity("Sevilla");
		toReturn.setTelephone("954123123");

		when(stubOwnerRepository.findById(1)).thenReturn(toReturn);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Owner owner = ownerService.findOwnerById(1);
		//3. Assert
		assertThat(owner).isEqualTo(toReturn);
		assertThat(owner).hasCity("Sevilla");
		assertThat(owner).hasAddress("Calle Larga 1");
		assertThat(owner).hasTelephone("954123123");
	}

	// findOwnerById(final int id) NEGATIVE TEST
	// id for which no owner exists in the repository. Should return null.
	@Test
	void shouldNotFindOwnerById() {
		//1. Arrange
		when(stubOwnerRepository.findById(1)).thenReturn(null);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Owner owner = ownerService.findOwnerById(1);
		//3. Assert
		assertThat(owner).isNull();
	}

	// findOwnerByUsername(final String username) POSITIVE TEST
	@Test
	void shouldFindOwnerByUsername() {
		//1. Arrange
		User user = new User();
		user.setUsername("jgarcia");
		user.setPassword("jgarcia");
		user.setEnabled(true);

		Owner toReturn = new Owner();
		toReturn.setId(1);
		toReturn.setFirstName("Juan");
		toReturn.setLastName("García");
		toReturn.setUser(user);
		toReturn.setAddress("Calle Larga 1");
		toReturn.setCity("Sevilla");
		toReturn.setTelephone("954123123");

		when(stubOwnerRepository.findByUsername("jgarcia")).thenReturn(toReturn);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Owner owner = ownerService.findOwnerByUsername("jgarcia");
		//3. Assert
		assertThat(owner).isEqualTo(toReturn);
		assertThat(owner).hasCity("Sevilla");
		assertThat(owner).hasAddress("Calle Larga 1");
		assertThat(owner).hasTelephone("954123123");
		assertThat(owner.getUser().getUsername()).isEqualTo("jgarcia");
		assertThat(owner.getUser().getPassword()).isEqualTo("jgarcia");
	}

	// findOwnerByUsername(final String username) NEGATIVE TEST
	// username for which no owner exists in the repository. Should return null.
	@Test
	void shouldNotFindOwnerByUsername() {
		//1. Arrange
		when(stubOwnerRepository.findByUsername("jgarcia")).thenReturn(null);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Owner owner = ownerService.findOwnerByUsername("jgarcia");
		//3. Assert
		assertThat(owner).isNull();
	}

	// findOwnerByLastName(final String lastName) POSITIVE TEST
	@Test
	void shouldFindOwnerByLastName() {
		//1. Arrange
		Owner o1 = new Owner();
		o1.setFirstName("Juan");
		o1.setLastName("García");

		Owner o2 = new Owner();
		o2.setFirstName("María");
		o2.setLastName("García");

		Owner o3 = new Owner();
		o3.setFirstName("Juan");
		o3.setLastName("Pérez");

		List<Owner> owners_garcia = Arrays.asList(o1, o2);

		when(stubOwnerRepository.findByLastName("García")).thenReturn(owners_garcia);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Collection<Owner> owners = ownerService.findOwnerByLastName("García");
		//3. Assert
		assertThat(owners).hasSize(2);
		assertThat(owners).contains(o1);
		assertThat(owners).contains(o2);
		assertThat(owners).doesNotContain(o3);
	}

	// findOwnerByLastName(final String lastName) POSITIVE TEST
	// Last name that doesn't exist in the repository. Should return an empty
	// list of owners.
	@Test
	void shouldNotFindOwnerByLastName() {
		//1. Arrange
		List<Owner> owners_garcia = Arrays.asList();

		when(stubOwnerRepository.findByLastName("García")).thenReturn(owners_garcia);

		ownerService = new OwnerService(stubOwnerRepository);
		//2. Act
		Collection<Owner> owners = ownerService.findOwnerByLastName("García");
		//3. Assert
		assertThat(owners).hasSize(0);
	}

}
