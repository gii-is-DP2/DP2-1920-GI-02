package org.group2.petclinic.repository;

import org.group2.petclinic.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
