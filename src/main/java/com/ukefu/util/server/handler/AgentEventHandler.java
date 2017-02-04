package com.ukefu.util.server.handler;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.ukefu.core.UKDataContext;
import com.ukefu.service.acd.ServiceQuene;
import com.ukefu.service.cache.CacheHelper;
import com.ukefu.service.repository.AgentUserRepository;
import com.ukefu.service.repository.ChatMessageRepository;
import com.ukefu.util.OnlineUserUtils;
import com.ukefu.util.UKTools;
import com.ukefu.util.client.NettyClients;
import com.ukefu.util.server.message.AgentServiceMessage;
import com.ukefu.util.server.message.AgentStatusMessage;
import com.ukefu.util.server.message.ChatMessage;
import com.ukefu.web.model.AgentStatus;
import com.ukefu.web.model.AgentUser;
import com.ukefu.web.model.MessageOutContent;
  
public class AgentEventHandler 
{  
	protected SocketIOServer server;
	
    @Autowired  
    public AgentEventHandler(SocketIOServer server)   
    {  
    	this.server = server ;
    }  
    
    @OnConnect  
    public void onConnect(SocketIOClient client)  
    {  
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
		if(!StringUtils.isBlank(user)){
			client.set("agentno", user);
			NettyClients.getInstance().putAgentEventClient(user, client);
		}
    }  
      
    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息  
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {  
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
		String orgi = client.getHandshakeData().getSingleUrlParam("orgi") ;
		if(!StringUtils.isBlank(user)){
			try {
				OnlineUserUtils.offline(user, orgi);
			} catch (Exception e) {
				e.printStackTrace();
			}
			NettyClients.getInstance().removeAgentEventClient(user , client.getSessionId().toString());
		}
    }  
      
    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "service")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentServiceMessage data)   
    {
    	
    }  
    
	//消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "status")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentStatusMessage data)   
    {
    	
    }
    
  //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "message")  
    public void onEvent(SocketIOClient client, AckRequest request, ChatMessage data)   
    {
    	AgentUser agentUser = (AgentUser) CacheHelper.getAgentUserCacheBean().getCacheObject(data.getTouser(), data.getOrgi());
    	MessageOutContent outMessage = new MessageOutContent() ;
    	outMessage.setMessage(data.getMessage());
    	outMessage.setMessageType(UKDataContext.MessageTypeEnum.MESSAGE.toString());
    	outMessage.setCalltype(UKDataContext.CallTypeEnum.OUT.toString());
    	outMessage.setAgentUser(agentUser);
    	outMessage.setSnsAccount(null);
    	AgentStatus agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(data.getUserid(), data.getOrgi()) ;
    	
    	if(agentUser == null){
    		agentUser = UKDataContext.getContext().getBean(AgentUserRepository.class).findById(data.getTouser()) ;
    		try {
				ServiceQuene.serviceFinish(agentUser, data.getOrgi());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	if(agentUser!=null){
    		data.setId(UKTools.getUUID());
    		data.setContextid(agentUser.getContextid());
    		data.setCalltype(UKDataContext.CallTypeEnum.IN.toString());
    		if(!StringUtils.isBlank(agentUser.getAgentno())){
    			data.setTouser(agentUser.getUserid());
    		}
    		data.setChannel(agentUser.getChannel());
    		
    		data.setSession(agentUser.getId());
    		
    		outMessage.setContextid(agentUser.getContextid());
    		outMessage.setFromUser(data.getUserid());
    		outMessage.setToUser(data.getTouser());
    		outMessage.setChannelMessage(data);
    		if(agentStatus!=null){
        		data.setUsername(agentStatus.getUsername());
        		outMessage.setNickName(agentStatus.getUsername());
        	}else{
        		outMessage.setNickName(data.getUsername());
        	}
    		outMessage.setCreatetime(data.getCreatetime());
    		
    		/**
    		 * 保存消息
    		 */
    		UKDataContext.getContext().getBean(ChatMessageRepository.class).save(data) ;

    		client.sendEvent(UKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
    		
	    	if(!StringUtils.isBlank(data.getTouser())){
	    		NettyClients.getInstance().sendIMEventMessage(data.getTouser(), UKDataContext.MessageTypeEnum.MESSAGE.toString(), outMessage);
	    	}
    	}
    }  
}  