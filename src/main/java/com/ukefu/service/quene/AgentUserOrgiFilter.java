package com.ukefu.service.quene;

import org.apache.commons.lang.StringUtils;

import com.hazelcast.mapreduce.KeyPredicate;
import com.ukefu.service.cache.CacheHelper;
import com.ukefu.web.model.AgentUser;

public class AgentUserOrgiFilter implements KeyPredicate<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1236581634096258855L;
	private String orgi ;
	private String status ;
	/**
	 * 
	 */
	public AgentUserOrgiFilter(String orgi , String status){
		this.orgi = orgi ;
		this.status = status ;
	}
	public boolean evaluate(String key) {
		AgentUser user = (AgentUser) CacheHelper
				.getAgentUserCacheBean().getCacheObject(key , orgi);
		return !StringUtils.isBlank(orgi) && orgi.equals(user.getOrgi()) && user.getStatus()!=null && user.getStatus().equals(status);
	}
}