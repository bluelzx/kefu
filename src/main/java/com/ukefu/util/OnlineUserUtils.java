package com.ukefu.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.hazelcast.config.Config;
import com.ukefu.core.UKDataContext;
import com.ukefu.service.impl.AgentUserService;
import com.ukefu.service.impl.HazelcastService;
import com.ukefu.service.repository.OnlineUserHisRepository;
import com.ukefu.service.repository.OnlineUserRepository;
import com.ukefu.util.router.RouterHelper;
import com.ukefu.util.server.message.NewRequestMessage;
import com.ukefu.web.model.AgentUser;
import com.ukefu.web.model.MessageDataBean;
import com.ukefu.web.model.MessageInContent;
import com.ukefu.web.model.OnlineUser;
import com.ukefu.web.model.OnlineUserHis;
import com.ukefu.web.model.User;

public class OnlineUserUtils {
	
	/**
	 * 
	 * @param user
	 * @param orgi
	 * @param id
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public static OnlineUser user(com.ukefu.web.model.User user ,String orgi ,String id, OnlineUserRepository service) throws Exception{
		return service.findBySessionid(id) ;
	}
	/**
	 * 
	 * @param user
	 * @param orgi
	 * @param optype
	 * @param request
	 * @param service
	 * @throws Exception
	 */
	public static OnlineUser online(User user ,String orgi , String sessionid ,String optype, HttpServletRequest request){
		
		OnlineUserRepository service = UKDataContext.getContext().getBean(OnlineUserRepository.class) ;
				
		OnlineUser onlineUser = new OnlineUser() ;
		onlineUser.setId(user.getId());
		onlineUser.setCreater(user.getId());
		onlineUser.setUsername(user.getUsername());
		onlineUser.setCreate_time(new Date());
		onlineUser.setUpdate_time(new Date());
		onlineUser.setUpdate_user(user.getUsername());
		onlineUser.setSessionid(sessionid);
		
		
		onlineUser.setOrgi(orgi) ;
		
		String cookie = getCookie(request, UKDataContext.GUEST_USER_ID_CODE) ;
		if(StringUtils.isBlank(cookie) || user.getSessionid().equals(cookie)){
			onlineUser.setOlduser("0") ;	//新用户 ： 老用户
		}else{
			onlineUser.setOlduser("1") ;	//新用户 ： 老用户
		}
		onlineUser.setMobile(CheckMobile.check(request.getHeader("User-Agent")) == true ? "1" : "0") ;	//新用户 ： 老用户
		
		onlineUser.setSource(user.getId()) ;
		
		String url = request.getHeader("Referer") ;
		onlineUser.setUrl( url) ;
		
		onlineUser.setUser_id(user.getId()) ;
		onlineUser.setUser_name(user.getUsername()) ;
		
		
		onlineUser.setLogintime(new Date()) ;
		onlineUser.setIp(request.getRemoteAddr()) ;
		
		IP ipdata = IPTools.getInstance().findGeography(request.getRemoteAddr()) ;
		onlineUser.setCountry(ipdata.getCountry()) ;
		onlineUser.setProvince(ipdata.getProvince()) ;
		onlineUser.setCity(ipdata.getCity()) ;
		onlineUser.setIsp(ipdata.getIsp()) ;
		onlineUser.setRegion(ipdata.toString()+"（"+request.getRemoteAddr()+"）") ;
//		
		onlineUser.setDatestr(new SimpleDateFormat("yyyMMdd").format(new Date())) ;
//		
		onlineUser.setHostname(request.getRemoteHost()) ;
		onlineUser.setSessionid(sessionid) ;
		onlineUser.setOptype(optype ) ;
		onlineUser.setStatus(UKDataContext.OnlineUserOperatorStatus.ONLINE.toString() ) ;
		
		OnlineUser tempOnlineUser = service.findBySessionid(onlineUser.getSessionid()) ;
		
		if(tempOnlineUser==null || StringUtils.isBlank(tempOnlineUser.getSessionid())){
			BrowserClient client = parseClient(request) ;
			onlineUser.setOper_system(client.getOs());
			onlineUser.setBrowser(client.getBrowser());
			onlineUser.setUseragent(client.getUseragent());
			
			service.save(onlineUser);
			
		}else{
			onlineUser = tempOnlineUser ;
			if(onlineUser.getLogintime()!=null){
				onlineUser.setStatus(UKDataContext.OnlineUserOperatorStatus.ONLINE.toString() ) ;
				onlineUser.setBetween_time((int) (new Date().getTime() - onlineUser.getLogintime().getTime()) ) ;
				onlineUser.setUpdate_time(new Date()) ;
				service.save(onlineUser);
			}
		}
		return onlineUser ;
	}
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request,String key){
		Cookie data = null ;
		if(request!=null && request.getCookies()!=null){
			for(Cookie cookie : request.getCookies()){
				if(cookie.getName().equals(key)){
					data = cookie ; break ;
				}
			}
		}
		return data!=null ? data.getValue() : null ;
	}
	/**
	 * 
	 * @param user
	 * @param orgi
	 * @throws Exception
	 */
	public static void offline(String user ,String orgi) throws Exception{
		OnlineUserRepository service = UKDataContext.getContext().getBean(OnlineUserRepository.class) ;
		
		OnlineUser onlineUser = service.findBySessionid(user) ;
		if(onlineUser!=null && !StringUtils.isBlank(onlineUser.getSessionid())){
			/**
			 * 删除当前在线用户信息
			 */
			service.delete(onlineUser) ;
			
			if(onlineUser != null && onlineUser.getLogintime()!=null){
				onlineUser.setStatus(UKDataContext.OnlineUserOperatorStatus.OFFLINE.toString() ) ;
				onlineUser.setBetween_time((int) (new Date().getTime() - onlineUser.getLogintime().getTime()) ) ;
				
				OnlineUserHis his = new OnlineUserHis() ;
				BeanUtils.copyProperties(onlineUser, his); 
				
				UKDataContext.getContext().getBean(OnlineUserHisRepository.class).save(his) ;
			}
		}
	}
	
	public static BrowserClient parseClient(HttpServletRequest request){
		BrowserClient client = new BrowserClient() ;
		String  browserDetails  =   request.getHeader("User-Agent");
	    String  userAgent       =   browserDetails;
	    String  user            =   userAgent.toLowerCase();
		String os = "";
        String browser = "";

        //=================OS=======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             os = "windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
             os = "mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
             os = "unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
             os = "android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
             os = "iphone";
         }else{
             os = "UnKnown, More-Info: "+userAgent;
         }
         //===============Browser===========================
        if (user.contains("msie") || user.indexOf("rv:11") > -1)
        {
        	if(user.indexOf("rv:11") >= 0){
        		browser = "IE11" ;
        	}else{
	            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
	            browser=substring.split(" ")[0].replace("MSIE", "IE")+substring.split(" ")[1];
        	}
        }else if (user.contains("trident"))
        {
            browser= "IE 11" ;
        }else if (user.contains("edge"))
        {
            browser= "Edge" ;
        }  else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        }else if ((user.indexOf("mozilla") > -1))
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
        	if(browserDetails.indexOf(" ") > 0){
        		browser = browserDetails.substring(0 , browserDetails.indexOf(" "));
        	}else{
        		browser = "Mozilla" ;
        	}

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            browser="ie";
        } else
        {
            browser = "UnKnown";
        }
        client.setUseragent(browserDetails);
        client.setOs(os);
        client.setBrowser(browser);
        
        return client ;
	}
	
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		try {
			tmp.append(java.net.URLDecoder.decode(src , "UTF-8") );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return tmp.toString();
	}

	public static String getKeyword(String url) {
		Map<String , String[]> values = new HashMap<String , String[]>();
		try {
			parseParameters(values , url , "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer strb = new StringBuffer();
		String[] data = values.get("q") ;
		if(data != null){
			for(String v : data){
				strb.append(v) ;
			}
		}
		return strb.toString();
	}
	public static String getSource(String url) {
		String source = "0" ;
		try {
			URL addr = new URL(url);
			source = addr.getHost() ;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return source;
	}
	
	public static NewRequestMessage newRequestMessage(String user , String orgi , String session , String appid , String ip , String osname , String browser) throws Exception {
    	//坐席服务请求，分配 坐席
		NewRequestMessage data = new NewRequestMessage() ;
		data.setAppid(appid);
		data.setOrgi(orgi);
		data.setUserid(user);
		data.setSession(session);
		data.setType(UKDataContext.MessageTypeEnum.NEW.toString());
    	data.setId(UKTools.genID());
    	
    	AgentUserService service = UKDataContext.getContext().getBean(AgentUserService.class) ;
		AgentUser agentUser = service.findByUserid(user);
		if(agentUser == null){
			agentUser = new AgentUser(data.getUserid() , UKDataContext.ChannelTypeEnum.WEBIM.toString() , data.getUserid() , null , data.getOrgi() , data.getAppid()) ;	//创建排队用户的信息，当前用户只能在队列里存在一次，用 UserID作为主键ID存储
			String userID = UKTools.genIDByKey(session);
			agentUser.setNickname("Guest_"+userID);
			agentUser.setUsername("Guest_"+userID);
			
			agentUser.setIpaddr(ip);
			agentUser.setOsname(osname);
			agentUser.setBrowser(browser);
			
			IP ipdata = IPTools.getInstance().findGeography(ip) ;
			
			if(ipdata!=null){
				agentUser.setCountry(ipdata.getCountry());
				agentUser.setProvince(ipdata.getProvince());
				agentUser.setCity(ipdata.getCity());
				agentUser.setRegion(ipdata.toString()+"["+ip+"]");
			}
			
			agentUser.setContextid(session);
			agentUser.setHeadimgurl("");
			agentUser.setId(data.getUserid());
		}
		agentUser.setStatus(null);	//修改状态
		
		MessageInContent inMessage = new MessageInContent() ;
		inMessage.setChannelMessage(data);
		inMessage.setAgentUser(agentUser);
		inMessage.setMessage(data.getMessage());
		inMessage.setFromUser(data.getUserid());
		inMessage.setToUser(data.getAppid());
		inMessage.setId(data.getId());
		inMessage.setMessageType(data.getType());
		inMessage.setNickName(agentUser.getNickname());
		inMessage.setOrgi(data.getOrgi());
		inMessage.setUser(agentUser);
		
		MessageDataBean outMessageDataBean = RouterHelper.getRouteInstance().handler(inMessage) ;
		
		if(outMessageDataBean!=null){
			data.setMessage(outMessageDataBean.getMessage());
		}
		return data;
    }
	
	public static void parseParameters(Map<String , String[]> map, String data, String encoding)
			throws UnsupportedEncodingException {
		if ((data == null) || (data.length() <= 0)) {
			return;
		}

		byte[] bytes = null;
		try {
			if (encoding == null)
				bytes = data.getBytes();
			else
				bytes = data.getBytes(encoding);
		} catch (UnsupportedEncodingException uee) {
		}
		parseParameters(map, bytes, encoding);
	}

	public static void parseParameters(Map<String, String[]> map, byte[] data, String encoding)
			throws UnsupportedEncodingException {
		if ((data != null) && (data.length > 0)) {
			int ix = 0;
			int ox = 0;
			String key = null;
			String value = null;
			while (ix < data.length) {
				byte c = data[(ix++)];
				switch ((char) c) {
				case '&':
					value = new String(data, 0, ox, encoding);
					if (key != null) {
						putMapEntry(map, key, value);
						key = null;
					}
					ox = 0;
					break;
				case '=':
					if (key == null) {
						key = new String(data, 0, ox, encoding);
						ox = 0;
					} else {
						data[(ox++)] = c;
					}
					break;
				case '+':
					data[(ox++)] = 32;
					break;
				case '%':
					data[(ox++)] = (byte) ((convertHexDigit(data[(ix++)]) << 4) + convertHexDigit(data[(ix++)]));

					break;
				default:
					data[(ox++)] = c;
				}
			}

			if (key != null) {
				value = new String(data, 0, ox, encoding);
				putMapEntry(map, key, value);
			}
		}
	}

	private static void putMapEntry(Map<String, String[]> map, String name, String value) {
		String[] newValues = null;
		String[] oldValues = (String[]) (String[]) map.get(name);
		if (oldValues == null) {
			newValues = new String[1];
			newValues[0] = value;
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	private static byte convertHexDigit(byte b) {
		if ((b >= 48) && (b <= 57))
			return (byte) (b - 48);
		if ((b >= 97) && (b <= 102))
			return (byte) (b - 97 + 10);
		if ((b >= 65) && (b <= 70))
			return (byte) (b - 65 + 10);
		return 0;
	}
	
//	public static void main(String[] args){
//		System.out.println(getKeyword("http://www.so.com/link?url=http%3A%2F%2Fwww.r3yun.com%2F&q=R3+Query%E5%AE%98%E7%BD%91&ts=1484181457&t=e2ad49617cd5de0eb0937f3e2a84669&src=haosou")) ;
//		System.out.println(getSource("https://www.google.com.hk/")) ;
//	}
}
