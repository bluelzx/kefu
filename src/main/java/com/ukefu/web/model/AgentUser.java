package com.ukefu.web.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ukefu.util.UKTools;

@Entity
@Table(name = "uk_agentuser")
@org.hibernate.annotations.Proxy(lazy = false)
public class AgentUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8657469468192323550L;
	private String id ;
	private String username;
	private String agentno ;
	private String userid ;
	private String channel ;
	private Date logindate ;
	private String source ;
	private Date endtime ;
	private String ipaddr ;
	private String osname ;
	private String browser ;
	
	private String nickname ;
	protected String city;
	protected String province;
	protected String country;
	protected String headimgurl;
	
	private String region ;
	
	private long sessiontimes=0 ;
	private int  waittingtime ;
	private int  tokenum ;
	
	private Date createtime = new Date() ;
	private Date updatetime;
	
	private String status ;		//用户状态，  服务中 (inservice)： 正在排队  inquene
	
	//add by cfh
	private String appid;
	
	private String sessiontype ;			
	private String contextid = UKTools.getUUID();		//会话ID ， 用户会话ID，在用户初次进入是分配，对话结束后失效
	private String agentserviceid;	//坐席服务ID，用于KPI
	private String orgi ;
	
	private long ordertime = System.currentTimeMillis();
	
	private String snsuser ;		//用户
	@Transient
	private Date lastmessage = new Date();
	
	private Date servicetime ;
	
	
	private Date waittingtimestart = new Date();
	
	@Transient
	private Date lastgetmessage = new Date();
	@Transient
	private String lastmsg ;
	@Transient
	private boolean tip = false ;

	//add by lzy 用来标识单次发送客服忙的提示消息 
	@Transient
	private boolean agentTip = false ;
	
	@Transient
	public boolean isAgentTip() {
		return agentTip;
	}
	public void setAgentTip(boolean agentTip) {
		this.agentTip = agentTip;
	}
	@Transient
	private boolean fromhis = false ;
	@Transient
	private boolean online = false ;
	@Transient
	private boolean disconnect = false ;
	@Transient
	private String agentskill ;
	public AgentUser(){}
	public AgentUser(String userid , String channel , String snsuser , String username , String orgi , String appid){
		this.userid = userid ;
		this.id = userid ;
		this.channel = channel ;
		this.snsuser = snsuser ;
		this.appid = appid;
		this.username = username ;
		this.orgi = orgi ;
	}
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Date getLogindate() {
		return logindate;
	}
	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	public String getContextid() {
		return contextid;
	}
	public void setContextid(String contextid) {
		this.contextid = contextid;
	}
	public String getAgentno() {
		return agentno;
	}
	public void setAgentno(String agentno) {
		this.agentno = agentno;
	}
	public String getAgentserviceid() {
		return agentserviceid;
	}
	public void setAgentserviceid(String agentserviceid) {
		this.agentserviceid = agentserviceid;
	}
	

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	@Transient
	public Date getLastmessage() {
		return lastmessage;
	}
	public void setLastmessage(Date lastmessage) {
		this.lastmessage = lastmessage;
	}
	@Transient
	public boolean isTip() {
		return tip;
	}
	public void setTip(boolean tip) {
		this.tip = tip;
	}
	@Transient
	public boolean isDisconnect() {
		return disconnect;
	}
	public void setDisconnect(boolean disconnect) {
		this.disconnect = disconnect;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public long getSessiontimes() {
		return sessiontimes;
	}
	public void setSessiontimes(long sessiontimes) {
		this.sessiontimes = sessiontimes;
	}
	@Transient
	public String getSessiontype() {
		return sessiontype;
	}
	public void setSessiontype(String sessiontype) {
		this.sessiontype = sessiontype;
	}
	public String getAgentskill() {
		return agentskill;
	}
	public void setAgentskill(String agentskill) {
		this.agentskill = agentskill;
	}
	@Transient
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	@Transient
	public boolean isFromhis() {
		return fromhis;
	}
	public void setFromhis(boolean fromhis) {
		this.fromhis = fromhis;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Transient
	public Date getLastgetmessage() {
		return lastgetmessage;
	}
	public void setLastgetmessage(Date lastgetmessage) {
		this.lastgetmessage = lastgetmessage;
	}
	@Transient
	public String getLastmsg() {
		return lastmsg;
	}
	public void setLastmsg(String lastmsg) {
		this.lastmsg = lastmsg;
	}
	public String getSnsuser() {
		return snsuser;
	}
	public void setSnsuser(String snsuser) {
		this.snsuser = snsuser;
	}
	public int getWaittingtime() {
		return waittingtime;
	}
	public void setWaittingtime(int waittingtime) {
		this.waittingtime = waittingtime;
	}
	public int getTokenum() {
		return tokenum;
	}
	public void setTokenum(int tokenum) {
		this.tokenum = tokenum;
	}
	@Transient
	public Date getWaittingtimestart() {
		return waittingtimestart;
	}
	public void setWaittingtimestart(Date waittingtimestart) {
		this.waittingtimestart = waittingtimestart;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname(String osname) {
		this.osname = osname;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	@Transient
	public String getTopic(){
		return new StringBuffer().append("/").append(this.orgi).append("/").append(this.agentno).toString() ;
	}
	@Transient
	public long getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(long ordertime) {
		this.ordertime = ordertime;
	}
	public Date getServicetime() {
		return servicetime;
	}
	public void setServicetime(Date servicetime) {
		this.servicetime = servicetime;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
}
