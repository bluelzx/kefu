package com.ukefu.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.User;

/**
 * 
 * @author admin
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findById(String id);

    User findByUsername(String username);
    
    Page<User> findAll(Pageable pageable);
    
    List<User> findBySkill(String skill) ;
}
