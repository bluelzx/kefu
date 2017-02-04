package com.ukefu.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.SNSAccount;


/**
 * 
 * @author admin
 *
 */
public interface SNSAccountRepository extends JpaRepository<SNSAccount, String> {

	SNSAccount findById(String id);
	
	List<SNSAccount> findBySnstype(String snstype);
}
