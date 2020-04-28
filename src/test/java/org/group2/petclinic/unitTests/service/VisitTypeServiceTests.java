
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.repository.VisitTypeRepository;
import org.group2.petclinic.service.VisitTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VisitTypeServiceTests {

	@Mock
	private VisitTypeRepository	stubVisitTypeRepository;

	protected VisitTypeService	visitTypeService;


	// findAll POSITIVE TEST
	@Test
	void shouldFindAll() {
		//1. Arrange
		List<VisitType> toReturn = new ArrayList<VisitType>();
		toReturn.add(mock(VisitType.class));
		toReturn.add(mock(VisitType.class));
		toReturn.add(mock(VisitType.class));

		when(stubVisitTypeRepository.findAll()).thenReturn(toReturn);

		visitTypeService = new VisitTypeService(stubVisitTypeRepository);
		//2. Act
		List<VisitType> visitTypes = (List<VisitType>) visitTypeService.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).isEqualTo(toReturn);
	}

	// findAll() NEGATIVE TEST
	@Test
	void shouldNotFindAll() {
		//1. Arrange
		when(stubVisitTypeRepository.findAll()).thenReturn(null);

		visitTypeService = new VisitTypeService(stubVisitTypeRepository);
		//2. Act
		List<VisitType> visitTypes = (List<VisitType>) visitTypeService.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).isNull();
	}

	// findVisitTypeById(final int id) POSITIVE TEST
	@Test
	void shouldFindVisitTypeById() {
		//1. Arrange
		VisitType toReturn = new VisitType();
		toReturn.setId(1);
		toReturn.setDuration(20);
		toReturn.setPrice(30.0);

		when(stubVisitTypeRepository.findVisitTypeById(1)).thenReturn(toReturn);

		visitTypeService = new VisitTypeService(stubVisitTypeRepository);
		//2. Act
		VisitType visitType = visitTypeService.findVisitTypeById(1);
		//3. Assert
		assertThat(visitType).isEqualTo(toReturn);
		assertThat(visitType).hasDuration(20);
		assertThat(visitType).hasPrice(30.0);
	}

	// findVisitTypeById(final int id) NEGATIVE TEST
	@Test
	void shouldNotFindVisitTypeById() {
		//1. Arrange
		when(stubVisitTypeRepository.findVisitTypeById(1)).thenReturn(null);

		visitTypeService = new VisitTypeService(stubVisitTypeRepository);
		//2. Act
		VisitType visitType = visitTypeService.findVisitTypeById(1);
		//3. Assert
		assertThat(visitType).isNull();
	}

}
