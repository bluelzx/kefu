package com.ukefu.web.handler.admin.users;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.service.repository.UserRepository;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.User;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
@Controller
@RequestMapping("/admin/user")
public class UsersController extends Handler{
	
	@Autowired
	private UserRepository userRepository;

    @RequestMapping("/index")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	map.addAttribute("userList", userRepository.findAll(new PageRequest(super.getP(request), super.getPs(request), Sort.Direction.ASC, "createtime")));
        return request(super.createAdminTempletResponse("/admin/user/index"));
    }
    
    @RequestMapping("/add")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
        return request(super.createRequestPageTempletResponse("/admin/user/add"));
    }
    
    @RequestMapping("/save")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView save(HttpServletRequest request ,@Valid User user) {
    	User tempUser = userRepository.findByUsername(user.getUsername()) ;
    	String msg = "admin_user_save_success" ;
    	if(tempUser != null){
    		msg =  "admin_user_save_exist";
    	}else{
    		user.setPassword(UKTools.md5(user.getPassword()));
    		userRepository.save(user) ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/user/index.html?msg="+msg));
    }
    
    @RequestMapping("/edit")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/admin/user/edit")) ;
    	view.addObject("userData", userRepository.findById(id)) ;
        return view;
    }
    
    @RequestMapping("/update")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView update(HttpServletRequest request ,@Valid User user) {
    	User tempUser = userRepository.findByUsername(user.getUsername()) ;
    	String msg = "admin_user_update_success" ;
    	if(tempUser != null){
	    	if(!tempUser.getId().equals(user.getId())){
	    		msg =  "admin_user_update_exist";
	    	}else{
	    		tempUser.setUname(user.getUname());
	    		tempUser.setUsername(user.getUsername());
	    		tempUser.setEmail(user.getEmail());
	    		tempUser.setMobile(user.getMobile());
	    		tempUser.setAgent(user.isAgent());
	    		if(!StringUtils.isBlank(user.getPassword())){
	    			tempUser.setPassword(UKTools.md5(user.getPassword()));
	    		}
	    		if(tempUser.getCreatetime() == null){
	    			tempUser.setCreatetime(new Date());
	    		}
	    		tempUser.setUpdatetime(new Date());
	    		userRepository.save(tempUser) ;
	    	}
    	}else{
    		msg =  "admin_user_update_not_exist";
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/user/index.html?msg="+msg));
    }
    
    @RequestMapping("/delete")
    @Menu(type = "admin" , subtype = "user")
    public ModelAndView delete(HttpServletRequest request ,@Valid User user) {
    	String msg = "admin_user_delete" ;
    	if(user!=null){
	    	userRepository.delete(user);
    	}else{
    		msg = "admin_user_not_exist" ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/user/index.html?msg="+msg));
    }
}