
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTests {

	@Mock
	private MedicineRepository	stubMedicineRepository;

	protected MedicineService	medicineService;

	// findAll POSITIVE TEST
		@Test
		void shouldFindAll() {
			//1. Arrange
			List<Medicine> toReturn = new ArrayList<Medicine>();
			toReturn.add(mock(Medicine.class));
			toReturn.add(mock(Medicine.class));
			toReturn.add(mock(Medicine.class));

			when(stubMedicineRepository.findAll()).thenReturn(toReturn);

			medicineService = new MedicineService(stubMedicineRepository);
			//2. Act
			List<Medicine> medicines = (List<Medicine>) medicineService.findMedicines();
			//3. Assert
			assertThat(medicines).isEqualTo(toReturn);
		}

		// findAll() NEGATIVE TEST
		// id for which no owner exists in the repository. Should return null.
		@Test
		void shouldNotFindOwnerById() {
			//1. Arrange
			when(stubMedicineRepository.findAll()).thenReturn(null);

			medicineService = new MedicineService(stubMedicineRepository);
			//2. Act
			List<Medicine> medicines = (List<Medicine>) medicineService.findMedicines();
			//3. Assert
			assertThat(medicines).isNull();
		}


}
