package com.ukefu.web.handler.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.service.acd.ServiceQuene;
import com.ukefu.service.cache.CacheHelper;
import com.ukefu.service.repository.UserRepository;
import com.ukefu.util.Menu;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.User;

@Controller
public class AdminController extends Handler{
	
	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping("/admin")
    public ModelAndView index(HttpServletRequest request) {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/admin/index"));
        User user = super.getUser(request) ;
        view.addObject("agentStatusReport",ServiceQuene.getAgentReport(user.getOrgi())) ;
		view.addObject("agentStatus",CacheHelper.getAgentStatusCacheBean().getCacheObject(user.getId(), user.getOrgi())) ;
        return view;
    }
    
    @RequestMapping("/admin/content")
    @Menu(type = "admin" , subtype = "content")
    public ModelAndView content(ModelMap map) {
    	return request(super.createAdminTempletResponse("/admin/content"));
    }

}