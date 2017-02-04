package com.ukefu.web.model;

import javax.persistence.Transient;

public class AgentService implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5052623717164550681L;
	private String agentid ;
	private String agentno ;	//当前分配的坐席号
	private String status ;		//当前状态 ， inservice ： inquene
	private long times ;		//用户接入时间
	private long servicetime ;	//开始和坐席对话的时间
	private String orgi ;
	private AgentUser agentUser ;
	
	public String getAgentno() {
		return agentno;
	}
	public void setAgentno(String agentno) {
		this.agentno = agentno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	public long getServicetime() {
		return servicetime;
	}
	public void setServicetime(long servicetime) {
		this.servicetime = servicetime;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	@Transient
	public AgentUser getAgentUser() {
		return agentUser;
	}
	public void setAgentUser(AgentUser agentUser) {
		this.agentUser = agentUser;
	}
	@Transient
	public String getTopic(){
		return new StringBuffer().append("/").append(this.orgi).append("/").append(this.agentno).toString() ;
	}
}
