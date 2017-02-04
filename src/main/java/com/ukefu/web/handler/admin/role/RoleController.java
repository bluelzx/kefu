package com.ukefu.web.handler.admin.role;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.service.repository.RoleRepository;
import com.ukefu.service.repository.UserRepository;
import com.ukefu.util.Menu;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.Role;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends Handler{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

    @RequestMapping("/index")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid String role) {
    	List<Role> roleList = roleRepository.findAll() ;
    	map.addAttribute("roleList", roleList);
    	if(roleList.size() > 0){
    		if(!StringUtils.isBlank(role)){
    			for(Role data : roleList){
    				if(data.getId().equals(role)){
    					map.addAttribute("roleData", data);
    				}
    			}
    		}else{
    			map.addAttribute("roleData", roleList.get(0));
    		}
    		
    		map.addAttribute("userList", userRepository.findAll());
    	}
        return request(super.createAdminTempletResponse("/admin/role/index"));
    }
    
    @RequestMapping("/add")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView add(ModelMap map , HttpServletRequest request) {
        return request(super.createRequestPageTempletResponse("/admin/role/add"));
    }
    
    @RequestMapping("/save")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView save(HttpServletRequest request ,@Valid Role role) {
    	Role tempRole = roleRepository.findByName(role.getName()) ;
    	String msg = "admin_role_save_success" ;
    	if(tempRole != null){
    		msg =  "admin_role_save_exist";
    	}else{
    		roleRepository.save(role) ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/role/index.html?msg="+msg));
    }
    
    @RequestMapping("/edit")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView edit(ModelMap map , @Valid String id) {
    	ModelAndView view = request(super.createRequestPageTempletResponse("/admin/role/edit")) ;
    	view.addObject("roleData", roleRepository.findById(id)) ;
        return view;
    }
    
    @RequestMapping("/update")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView update(HttpServletRequest request ,@Valid Role role) {
    	Role tempRole = roleRepository.findById(role.getId()) ;
    	String msg = "admin_role_update_success" ;
    	if(tempRole != null){
    		tempRole.setName(role.getName());
    		tempRole.setUpdatetime(new Date());
    		roleRepository.save(tempRole) ;
    	}else{
    		msg =  "admin_role_update_not_exist";
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/role/index.html?msg="+msg));
    }
    
    @RequestMapping("/delete")
    @Menu(type = "admin" , subtype = "role")
    public ModelAndView delete(HttpServletRequest request ,@Valid Role role) {
    	String msg = "admin_role_delete" ;
    	if(role!=null){
	    	roleRepository.delete(role);
    	}else{
    		msg = "admin_role_not_exist" ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/admin/role/index.html?msg="+msg));
    }
}