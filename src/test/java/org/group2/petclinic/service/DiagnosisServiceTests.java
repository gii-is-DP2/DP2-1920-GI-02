
package org.group2.petclinic.service;

import org.group2.petclinic.repository.DiagnosisRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiagnosisServiceTests {

	@Mock
	private DiagnosisRepository	stubDiagnosisRepository;

	protected DiagnosisService	diagnosisService;

	// saveDiagnosis(final Diagnosis diagnosis) NOT TESTED (implemented by springframework)


}
