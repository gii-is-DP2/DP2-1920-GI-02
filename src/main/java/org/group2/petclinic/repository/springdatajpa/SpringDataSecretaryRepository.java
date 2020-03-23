
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.repository.SecretaryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataSecretaryRepository extends SecretaryRepository, Repository<Secretary, Integer> {

	@Override
	@Query("SELECT s FROM Secretary s WHERE s.user.username =:username")
	Secretary findSecretaryByName(@Param("username") String username);

}
