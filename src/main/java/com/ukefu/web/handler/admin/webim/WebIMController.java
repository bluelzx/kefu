package com.ukefu.web.handler.admin.webim;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.service.repository.ConsultInviteRepository;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.web.handler.Handler;
import com.ukefu.web.model.CousultInvite;

@Controller
@RequestMapping("/admin/webim")
public class WebIMController extends Handler{
	
	@Autowired
	private ConsultInviteRepository invite;
	
	@Value("${web.upload-path}")
    private String path;

    @RequestMapping("/index")
    @Menu(type = "admin" , subtype = "webim")
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid String id) {
//    	List<SNSAccount> snsAccountList = snsAccountRepository.findBySnstype(UKDataContext.ChannelTypeEnum.WEBIM.toString()) ;
//    	if(snsAccountList.size() > 0){
//    		if(!StringUtils.isBlank(id)){
//    			for(SNSAccount data : snsAccountList){
//    				if(data.getId().equals(id)){
//    					map.addAttribute("snsAccount", data);
//    				}
//    			}
//    		}else{
//    			map.addAttribute("snsAccount", snsAccountList.get(0));
//    		}
//    	}
    	List<CousultInvite> settingList = invite.findAll() ;
    	if(settingList.size() > 0){
    		map.addAttribute("inviteData", settingList.get(0));
    	}
    	map.addAttribute("import", request.getServerPort()) ;
        return request(super.createAdminTempletResponse("/admin/webim/index"));
    }
    
    @RequestMapping("/save")
    @Menu(type = "admin" , subtype = "webim")
    public ModelAndView save(HttpServletRequest request , @Valid CousultInvite inviteData , @RequestParam(value = "webimlogo", required = false) MultipartFile webimlogo,@RequestParam(value = "agentheadimg", required = false) MultipartFile agentheadimg) throws IOException {
    	if(!StringUtils.isBlank(inviteData.getSnsaccountid())){
    		CousultInvite tempData = invite.findBySnsaccountid(inviteData.getSnsaccountid()) ;
    		if(tempData!=null){
    			UKTools.copyProperties(inviteData,tempData);
    			inviteData = tempData ;
    		}
    	}else{
    		inviteData.setSnsaccountid(super.getUser(request).getId());
    	}
    	inviteData.setOrgi(super.getUser(request).getOrgi());
    	if(webimlogo!=null && webimlogo.getOriginalFilename().lastIndexOf(".") > 0){
    		File logoDir = new File(path , "logo");
    		if(!logoDir.exists()){
    			logoDir.mkdirs() ;
    		}
    		String fileName = "logo/"+inviteData.getSnsaccountid()+webimlogo.getOriginalFilename().substring(webimlogo.getOriginalFilename().lastIndexOf(".")) ;
    		FileCopyUtils.copy(webimlogo.getBytes(), new File(path , fileName));
    		inviteData.setConsult_dialog_logo(fileName);
    	}
    	if(agentheadimg!=null && agentheadimg.getOriginalFilename().lastIndexOf(".") > 0){
    		File headimgDir = new File(path , "headimg");
    		if(!headimgDir.exists()){
    			headimgDir.mkdirs() ;
    		}
    		String fileName = "headimg/"+inviteData.getSnsaccountid()+agentheadimg.getOriginalFilename().substring(agentheadimg.getOriginalFilename().lastIndexOf(".")) ;
    		FileCopyUtils.copy(agentheadimg.getBytes(), new File(path , fileName));
    		inviteData.setConsult_dialog_headimg(fileName);
    	}
    	invite.save(inviteData) ;
        return request(super.createRequestPageTempletResponse("redirect:/admin/webim/index.html"));
    }
}