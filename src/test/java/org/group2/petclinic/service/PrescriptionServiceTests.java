
package org.group2.petclinic.service;

import org.group2.petclinic.repository.PrescriptionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTests {

	@Mock
	private PrescriptionRepository	stubPrescriptionRepository;

	protected PrescriptionService	prescriptionService;

	// savePrescription(final Prescription prescription) NOT TESTED (implemented by springframework)


}
