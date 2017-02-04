package com.ukefu.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ukefu.core.UKDataContext;
import com.ukefu.service.repository.UserRepository;
import com.ukefu.web.model.User;

/**
 *
 * @author UK
 * @version 1.0.0
 *
 */
@Controller
public class LoginController extends Handler{
	
	@Autowired
	private UserRepository userRepository;

    @RequestMapping(value = "/login" , method=RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }
    
    @RequestMapping(value = "/login" , method=RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response , @Valid User user , @RequestHeader(value = "referer", required = false) final String referer) {
        if(user!=null && user.getUsername()!=null){
	    	User loginUser = userRepository.findByUsername(user.getUsername()) ;
	        if(loginUser!=null){
	        	super.setUser(request, loginUser);
	        }
        }
    	return "redirect:/";
    }
    
    @RequestMapping("/logout")  
    public String logout(HttpServletRequest request  ){  
    	request.getSession().removeAttribute(UKDataContext.USER_SESSION_NAME) ;
         return "login";
    }  
    
}