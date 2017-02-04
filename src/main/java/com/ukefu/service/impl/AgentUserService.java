package com.ukefu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ukefu.service.repository.AgentUserRepository;
import com.ukefu.web.model.AgentUser;

@Service
public class AgentUserService {
	
	@Autowired
	private AgentUserRepository agentUserRepository;
	
	public AgentUser findByUserid(String userid){
		return agentUserRepository.findByUserid(userid) ;
	}
	
	public void save(AgentUser agentUser){
		agentUserRepository.save(agentUser) ;
	}
	
	public void delete(String id){
		agentUserRepository.delete(id);
	}
}
