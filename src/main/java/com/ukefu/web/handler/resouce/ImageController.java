package com.ukefu.web.handler.resouce;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ukefu.util.Menu;
import com.ukefu.web.handler.Handler;

@Controller
@RequestMapping("/res")
public class ImageController extends Handler{
	
	@Value("${web.upload-path}")
    private String path;
	
    @RequestMapping("/image")
    @Menu(type = "resouce" , subtype = "image" , access = true)
    public void index(HttpServletResponse response, @Valid String id) throws IOException {
    	response.getOutputStream().write(FileUtils.readFileToByteArray(new File(path ,id)));
    }
    
}