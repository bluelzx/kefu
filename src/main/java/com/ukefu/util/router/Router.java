package com.ukefu.util.router;

import com.ukefu.web.model.MessageDataBean;

public abstract class Router {
	
	public abstract MessageDataBean handler(MessageDataBean message) ;
}
