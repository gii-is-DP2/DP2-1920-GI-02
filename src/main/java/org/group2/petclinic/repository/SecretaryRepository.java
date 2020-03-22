
package org.group2.petclinic.repository;

import org.group2.petclinic.model.Secretary;
import org.springframework.dao.DataAccessException;

public interface SecretaryRepository {

	Secretary findSecretaryByName(String username) throws DataAccessException;

}
