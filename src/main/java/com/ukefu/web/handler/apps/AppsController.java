package com.ukefu.web.handler.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.service.repository.UserRepository;
import com.ukefu.util.Menu;
import com.ukefu.web.handler.Handler;

@Controller
public class AppsController extends Handler{
	
	@Autowired
	private UserRepository userRepository;

    @RequestMapping("/apps/content")
    @Menu(type = "apps" , subtype = "content")
    public ModelAndView content(ModelMap map) {
    	return request(super.createAppsTempletResponse("/apps/content"));
    }

}