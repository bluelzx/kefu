package com.ukefu.util.server.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ukefu.util.UKTools;

@Entity
@Table(name = "uk_chat_message")
@org.hibernate.annotations.Proxy(lazy = false)
public class ChatMessage implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String id = UKTools.getUUID();
	private String appid ;
	private String userid ;
	private String username ;
	
	
	private String touser ;
	private String tousername ;
	private String session ;
	private String message ;
	private String orgi ;
	private String channel ;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTousername() {
		return tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	private String type ;		// 类型有两种 ， 一种 message ， 一种 writing
	private String contextid ;
	private String calltype ;
	private String createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContextid() {
		return contextid;
	}

	public void setContextid(String contextid) {
		this.contextid = contextid;
	}

	public String getCalltype() {
		return calltype;
	}

	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
