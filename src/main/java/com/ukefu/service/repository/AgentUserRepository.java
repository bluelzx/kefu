package com.ukefu.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.AgentUser;


/**
 * 
 * @author admin
 *
 */
public interface AgentUserRepository extends JpaRepository<AgentUser, String> {
	AgentUser findById(String id);
	
	AgentUser findByUserid(String userid) ;
	
	List<AgentUser> findByAgentno(String agentno) ;
}
