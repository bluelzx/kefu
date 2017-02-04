package com.ukefu.web.handler.apps.im;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.service.repository.ConsultInviteRepository;
import com.ukefu.util.Menu;
import com.ukefu.util.OnlineUserUtils;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.CousultInvite;
import com.ukefu.web.model.OnlineUser;

@Controller
@RequestMapping("/im")
public class IMController extends Handler{
	
	@Value("${uk.im.server.host}")  
    private String host;  
  
    @Value("${uk.im.server.port}")  
    private Integer port; 
	
	@Autowired
	private ConsultInviteRepository inviteRepository;

    @RequestMapping("/{id}")
    @Menu(type = "im" , subtype = "point" , access = true)
    public ModelAndView point(HttpServletRequest request , HttpServletResponse response, @PathVariable String id , @Valid String orgi , @Valid String userid) {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/apps/im/point")) ; 
    	
    	view.addObject("hostname", request.getServerName()) ;
		view.addObject("port", request.getServerPort()) ;
		view.addObject("appid", id) ;
		
		CousultInvite invite = inviteRepository.findOne(id) ;
    	if(invite!=null){
    		view.addObject("inviteData", invite);
    		view.addObject("orgi",invite.getOrgi());
    		view.addObject("appid",id);
    	}
		
        return view;
    }
    /**
     * 延时获取用户端浏览器的跟踪ID
     * @param request
     * @param response
     * @param orgi
     * @param appid
     * @param userid
     * @param sign
     * @return
     */
    @RequestMapping("/online")
    @Menu(type = "im" , subtype = "online" , access = true)
    public @ResponseBody OnlineUser online(HttpServletRequest request , HttpServletResponse response , @Valid String orgi , @Valid String appid, @Valid String userid , @Valid String sign){
    	OnlineUser onlineUser = null ;
    	if(!StringUtils.isBlank(sign)){
    		onlineUser = OnlineUserUtils.online(super.getIMUser(request , sign) , orgi ,sign , UKDataContext.OnlineUserTypeStatus.WEBIM.toString(), request);
    	}
    	return onlineUser;
    }
    
    @RequestMapping("/index")
    @Menu(type = "im" , subtype = "index" , access = true)
    public ModelAndView index(HttpServletRequest request , HttpServletResponse response, @Valid String orgi , @Valid String appid, @Valid String userid, @Valid String sessionid) {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/apps/im/index")) ; 
    	
    	view.addObject("hostname", request.getServerName()) ;
		view.addObject("port", port) ;
		view.addObject("appid", appid) ;
		view.addObject("userid", userid) ;
		view.addObject("sessionid", sessionid) ;
		
		CousultInvite invite = inviteRepository.findOne(appid) ;
    	if(invite!=null){
    		view.addObject("inviteData", invite);
    		view.addObject("orgi",invite.getOrgi());
    		view.addObject("appid",invite.getSnsaccountid());
    	}
		
        return view;
    }
}