
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.repository.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VetServiceTests {

	@Mock
	private VetRepository	stubVetRepository;

	protected VetService	vetService;


	// findVetByUsername(final String username) POSITIVE TEST
	@Test
	void shouldFindVetByUsername() {
		//1. Arrange
		User user = new User();
		user.setUsername("jgarcia");
		user.setPassword("jgarcia");
		user.setEnabled(true);

		Vet toReturn = new Vet();
		toReturn.setId(1);
		toReturn.setFirstName("Juan");
		toReturn.setLastName("García");
		toReturn.setUser(user);

		when(stubVetRepository.findByUsername("jgarcia")).thenReturn(toReturn);

		vetService = new VetService(stubVetRepository);
		//2. Act
		Vet vet = vetService.findVetByUsername("jgarcia");
		//3. Assert
		assertThat(vet).isEqualTo(toReturn);
	}

	// findVetByUsername(final String username) NEGATIVE TEST
	// username for which no vet exists in the repository. Should return null.
	@Test
	void shouldNotFindVetByUsername() {
		//1. Arrange
		when(stubVetRepository.findByUsername("jgarcia")).thenReturn(null);

		vetService = new VetService(stubVetRepository);
		//2. Act
		Vet vet = vetService.findVetByUsername("jgarcia");
		//3. Assert
		assertThat(vet).isNull();
	}

	// findVets() POSITIVE TEST
	@Test
	void shouldFindVets() {
		//1. Arrange
		Vet v1 = new Vet();
		v1.setId(1);
		Vet v2 = new Vet();
		v2.setId(2);
		Vet v3 = new Vet();
		v3.setId(3);
		Vet v4 = new Vet();
		v4.setId(4);

		List<Vet> toReturn = Arrays.asList(v1, v2, v3);

		when(stubVetRepository.findAll()).thenReturn(toReturn);

		vetService = new VetService(stubVetRepository);
		//2. Act
		Collection<Vet> vets = vetService.findVets();
		//3. Assert
		assertThat(vets).hasSize(3);
		assertThat(vets).contains(v1, v2, v3);
		assertThat(vets).doesNotContain(v4);
	}

	// findVets() NEGATIVE TEST
	// No vets in the repository.
	@Test
	void shouldNotFindVets() {
		//1. Arrange
		List<Vet> toReturn = Arrays.asList();

		when(stubVetRepository.findAll()).thenReturn(toReturn);

		vetService = new VetService(stubVetRepository);
		//2. Act
		Collection<Vet> vets = vetService.findVets();
		//3. Assert
		assertThat(vets).hasSize(0);
	}

}
