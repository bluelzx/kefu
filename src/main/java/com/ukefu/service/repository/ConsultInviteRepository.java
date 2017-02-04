package com.ukefu.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.CousultInvite;


/**
 * 
 * @author admin
 *
 */
public interface ConsultInviteRepository extends JpaRepository<CousultInvite, String> {
	CousultInvite findBySnsaccountid(String snsaccountid);
}
