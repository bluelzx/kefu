package com.ukefu.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.Role;


/**
 * 
 * @author admin
 *
 */
public interface RoleRepository extends JpaRepository<Role, String> {

	Role findById(String id);
	
    Role findByName(String name);
}
