
package org.group2.petclinic.repository;

import org.group2.petclinic.model.Creditcard;
import org.springframework.dao.DataAccessException;

public interface CreditcardRepository {

	void save(Creditcard creditcard) throws DataAccessException;

}
