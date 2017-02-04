package com.ukefu.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.OnlineUser;


/**
 * 
 * @author admin
 *
 */
public interface OnlineUserRepository extends JpaRepository<OnlineUser, String> {

	OnlineUser findBySessionid(String sessionid);
}
