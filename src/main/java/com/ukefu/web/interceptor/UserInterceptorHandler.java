package com.ukefu.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.web.model.User;

public class UserInterceptorHandler extends HandlerInterceptorAdapter {
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	boolean filter = false; 
        User user = (User) request.getSession(true).getAttribute(UKDataContext.USER_SESSION_NAME) ;
        HandlerMethod  handlerMethod = (HandlerMethod ) handler ;
        Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
        if(user != null || (menu!=null && menu.access()) || handlerMethod.getBean() instanceof BasicErrorController){
        	filter = true;
        }
        
        if(!filter){
        	response.sendRedirect("/login.html");
        }
        return filter ; 
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2,
            ModelAndView view) throws Exception {
    	User user = (User) arg0.getSession().getAttribute(UKDataContext.USER_SESSION_NAME) ;
    	if(user!=null && view!=null){
			view.addObject("user", user) ;
			view.addObject("schema",arg0.getScheme()) ;
			view.addObject("hostname",arg0.getServerName()) ;
			view.addObject("port",arg0.getServerPort()) ;
			
			HandlerMethod  handlerMethod = (HandlerMethod ) arg2 ;
			Menu menu = handlerMethod.getMethod().getAnnotation(Menu.class) ;
			if(menu!=null){
				view.addObject("subtype", menu.subtype()) ;
				view.addObject("maintype", menu.type()) ;
			}
			view.addObject("orgi", user.getOrgi()) ;
		}
    	view.addObject("webimport",UKDataContext.getWebIMPort()) ;
    	view.addObject("sessionid", UKTools.getContextID(arg0.getSession().getId())) ;
		/**
		 * WebIM共享用户
		 */
		User imUser = (User) arg0.getSession().getAttribute(UKDataContext.IM_USER_SESSION_NAME) ;
		if(imUser == null && view!=null){
			imUser = new User();
			imUser.setUsername(UKDataContext.GUEST_USER) ;
			imUser.setId(UKTools.getContextID(arg0.getSession(true).getId())) ;
			imUser.setSessionid(imUser.getId()) ;
			view.addObject("imuser", imUser) ;
		}
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}