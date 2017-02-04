package com.ukefu.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.Skill;


/**
 * 
 * @author admin
 *
 */
public interface SkillRepository extends JpaRepository<Skill, String> {

    Skill findById(String id);

    Page<Skill> findAll(Pageable pageable);

	Skill findByName(String name);
	
}
