package com.ukefu.web.handler.apps.agent;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.service.acd.ServiceQuene;
import com.ukefu.service.cache.CacheHelper;
import com.ukefu.service.repository.AgentUserRepository;
import com.ukefu.service.repository.ChatMessageRepository;
import com.ukefu.util.Menu;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.AgentStatus;
import com.ukefu.web.model.AgentUser;
import com.ukefu.web.model.User;

@Controller
@RequestMapping("/agent")
public class AgentController extends Handler {
	
	@Autowired
	private AgentUserRepository agentUserRepository ; 
	
	@Autowired
	private ChatMessageRepository chatMessageRepository ;
	
	@RequestMapping("/index")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = request(super.createAppsTempletResponse("/apps/agent/index")) ; 
		User user = super.getUser(request) ;
		List<AgentUser> agentUserList = agentUserRepository.findByAgentno(user.getId());
		view.addObject("agentUserList", agentUserList) ;
		
		if(agentUserList.size() > 0){
			AgentUser agentUser = agentUserList.get(0) ;
			view.addObject("curagentuser", agentUser) ;
			
			view.addObject("agentUserMessageList", chatMessageRepository.findBySession(agentUser.getId())) ;
		}
		return view ;
	}
	
	@RequestMapping("/agentusers")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView agentusers(HttpServletRequest request , String userid) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/agent/agentusers")) ;
		User user = super.getUser(request) ;
		view.addObject("agentUserList", agentUserRepository.findByAgentno(user.getId())) ;
		view.addObject("curagentuser", agentUserRepository.findByUserid(userid)) ;
		return view ;
	}
	
	@RequestMapping("/agentuser")
	@Menu(type = "apps", subtype = "agent")
	public ModelAndView agentuser(HttpServletRequest request , String id) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/agent/mainagentuser")) ;
		AgentUser agentUser = agentUserRepository.findById(id);
		view.addObject("curagentuser", agentUser) ;
		view.addObject("agentUserMessageList", chatMessageRepository.findBySession(agentUser.getId())) ;

		return view ;
	}
	
	@RequestMapping(value="/ready")  
	@Menu(type = "apps", subtype = "agent")
    public ModelAndView ready(HttpServletRequest request){ 
    	AgentStatus agentStatus = new AgentStatus() ;
    	User user = super.getUser(request) ;
    	agentStatus.setId(user.getId());
    	agentStatus.setUserid(user.getId());
    	agentStatus.setUsername(user.getUsername());
    	agentStatus.setAgentno(user.getId());
    	agentStatus.setLogindate(new Date());
    	agentStatus.setOrgi(user.getOrgi());
    	agentStatus.setStatus(UKDataContext.AgentStatusEnum.READY.toString());
    	CacheHelper.getAgentStatusCacheBean().put(agentStatus.getId(), agentStatus, user.getOrgi());
    	
    	ServiceQuene.allotAgent(agentStatus, user.getOrgi());
    	
    	return request(super.createAppsTempletResponse("/public/success")) ; 
    }
	
	@RequestMapping(value="/notready") 
	@Menu(type = "apps", subtype = "agent")
    public ModelAndView notready(HttpServletRequest request){ 
		User user = super.getUser(request) ;
    	CacheHelper.getAgentStatusCacheBean().delete(super.getUser(request).getId(), user.getOrgi());;
    	ServiceQuene.publishMessage(user.getOrgi());
    	return request(super.createAppsTempletResponse("/public/success")) ; 
    }
}