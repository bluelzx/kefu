package com.ukefu.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.AgentStatus;


/**
 * 
 * @author admin
 *
 */
public interface AgentStatusRepository extends JpaRepository<AgentStatus, String> {
	AgentStatus findById(String id);
}
