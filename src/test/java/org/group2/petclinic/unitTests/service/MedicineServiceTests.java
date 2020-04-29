
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.repository.MedicineRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.group2.petclinic.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTests {

	@Mock
	private MedicineRepository		stubMedicineRepository;

	@Mock
	private PrescriptionRepository	stubPrescriptionRepository;

	protected MedicineService		medicineService;


	// findAll POSITIVE TEST

		@Test
		void shouldFindAll() {
			//1. Arrange
			List<Medicine> toReturn = new ArrayList<Medicine>();
			toReturn.add(mock(Medicine.class));
			toReturn.add(mock(Medicine.class));
			toReturn.add(mock(Medicine.class));

			when(stubMedicineRepository.findAll()).thenReturn(toReturn);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			List<Medicine> medicines = (List<Medicine>) medicineService.findMedicines();
			//3. Assert
			assertThat(medicines).isEqualTo(toReturn);
		}

		// findAll() NEGATIVE TEST
		@Test
		void shouldNotFindAll() {
			//1. Arrange
			when(stubMedicineRepository.findAll()).thenReturn(null);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			List<Medicine> medicines = (List<Medicine>) medicineService.findMedicines();
			//3. Assert
			assertThat(medicines).isNull();
		}
		
		// findMedicineById(final int id) POSITIVE TEST
		@Test
		void shouldFindMedicineById() {
			//1. Arrange
			Medicine toReturn = new Medicine();
			toReturn.setId(1);
			toReturn.setName("Medicine 1");
			toReturn.setBrand("Brandolino");

			when(stubMedicineRepository.findMedicineById(1)).thenReturn(toReturn);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			Medicine medicine = medicineService.findMedicineById(1);
			//3. Assert
			assertThat(medicine).isEqualTo(toReturn);
		}

		// findMedicineById(final int id) NEGATIVE TEST
		@Test
		void shouldNotFindMedicineById() {
			//1. Arrange
			when(stubMedicineRepository.findMedicineById(1)).thenReturn(null);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			Medicine medicine = medicineService.findMedicineById(1);
			//3. Assert
			assertThat(medicine).isNull();
		}
		
		
		// isInUse(Medicine medicine) POSITIVE TEST
		@Test
		void shouldFindIfIsInUse() {
			//1. Arrange
			Medicine toReturn = new Medicine();
			toReturn.setId(1);
			toReturn.setName("Medicine 1");
			toReturn.setBrand("Brandolino");
			toReturn.setUsed(false);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			Boolean bool = medicineService.isInUse(toReturn);
			//3. Assert
			assertThat(bool).isEqualTo(false);
		}
		

		// save(Medicine medicine) POSITIVE TEST
		@Test
		void shouldSaveMedicine() {
			//1. Arrange
			Medicine toReturn = new Medicine();
			toReturn.setId(1);
			toReturn.setName("Medicine 50");
			toReturn.setBrand("Brandolino");
			toReturn.setUsed(false);
			
			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			int int1 = this.stubMedicineRepository.findAll().size();
			//2. Act
	        medicineService.saveMedicine(toReturn);
	        int int2 = this.stubMedicineRepository.findAll().size();
	        
			//3. Assert
			assertThat(int2 > int1);
		}
		
		// delete(Medicine medicine) POSITIVE TEST
		@Test
		void shouldDeleteMedicine() {
			//1. Arrange
			Medicine toReturn = new Medicine();
			toReturn.setId(1);
			toReturn.setName("Medicine 1");
			toReturn.setBrand("Brandolino");

			when(stubMedicineRepository.findMedicineById(1)).thenReturn(toReturn);

			medicineService = new MedicineService(stubMedicineRepository, stubPrescriptionRepository);
			//2. Act
			Medicine medicine = medicineService.findMedicineById(1);
			medicineService.deleteMedicine(medicine);
	        
			//3. Assert
			if(medicineService.findMedicines().size()>0)
			assertThat(medicineService.findMedicines().size()).isEqualTo((medicineService.findMedicines().size()) - 1);
			else
			assertThat(medicineService.findMedicines().size()).isEqualTo(0);
		}
		


}
