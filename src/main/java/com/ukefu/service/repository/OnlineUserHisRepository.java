package com.ukefu.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.web.model.OnlineUserHis;


/**
 * 
 * @author admin
 *
 */
public interface OnlineUserHisRepository extends JpaRepository<OnlineUserHis, String> {

	OnlineUserHis findById(String id);
}
