
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VetServiceTests {

	@Autowired
	protected VetService vetService;


	@Test
	void shouldFindVetByUsername() {
		//1. Arrange
		//2. Act
		Vet vet = vetService.findVetByUsername("vet1");
		//3. Assert
		assertThat(vet.getFirstName()).isEqualTo("James");
		assertThat(vet.getLastName()).isEqualTo("Carter");
	}

	@Test
	void shouldFindVets() {
		//1. Arange
		//2. Act
		Collection<Vet> vets = this.vetService.findVets();
		//3. Assert
		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		assertThat(vet.getLastName()).isEqualTo("Douglas");
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}

}
