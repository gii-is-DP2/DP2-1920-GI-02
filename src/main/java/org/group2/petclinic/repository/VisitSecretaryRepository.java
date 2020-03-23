
package org.group2.petclinic.repository;

import org.group2.petclinic.model.Visit;
import org.springframework.dao.DataAccessException;

public interface VisitSecretaryRepository {

	Iterable<Visit> findVisitsNoPayment() throws DataAccessException;

	Visit findVisitById(int id) throws DataAccessException;

}
