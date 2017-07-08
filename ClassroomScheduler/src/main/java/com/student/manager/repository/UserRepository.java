package com.student.manager.repository;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.student.manager.model.Users;

/**
 * @author Veera Marisetty
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends Repository<Users, Long> {

	Users save(Users users);

	Users findByName(String name);
	
}
