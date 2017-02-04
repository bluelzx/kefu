package com.ukefu.util.router;

import com.ukefu.web.model.MessageDataBean;
import com.ukefu.web.model.MessageOutContent;

public class AgentUserRouter extends Router{

	@Override
	public MessageDataBean handler(MessageDataBean message) {
		MessageDataBean outMessage = new MessageOutContent() ;
		return outMessage;
	}

}
