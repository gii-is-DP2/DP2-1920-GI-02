package org.group2.petclinic.repository;

import org.group2.petclinic.model.Authorities;
import org.springframework.data.repository.CrudRepository;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}
