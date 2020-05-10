
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.repository.DiagnosisRepository;
import org.group2.petclinic.service.DiagnosisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DiagnosisServiceTests {

	@Mock
	private DiagnosisRepository	stubDiagnosisRepository;

	protected DiagnosisService	diagnosisService;


	// save(Diagnosis diagnosis) POSITIVE TEST
	@Test
	void shouldSaveDiagnosis() {
		// 1. Arrange
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		Diagnosis toReturn = new Diagnosis();
		toReturn.setId(12);
		toReturn.setDate(LocalDate.parse("2013/01/01", formatter));
		toReturn.setDescription("EY");

		diagnosisService = new DiagnosisService(stubDiagnosisRepository);
		int int1 = this.stubDiagnosisRepository.findAll().size();
		// 2. Act
		diagnosisService.saveDiagnosis(toReturn);
		int int2 = this.stubDiagnosisRepository.findAll().size();

		// 3. Assert
		assertThat(int2 > int1);
	}

}
