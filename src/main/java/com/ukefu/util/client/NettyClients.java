package com.ukefu.util.client;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;


public class NettyClients {
	
	private static NettyClients clients = new NettyClients();
	
	private NettyIMClient imClients = new NettyIMClient();
	private NettyAgentClient agentClients = new NettyAgentClient();
	
	public static NettyClients getInstance(){
		return clients ;
	}

	public void setImClients(NettyIMClient imClients) {
		this.imClients = imClients;
	}

	public void setAgentClients(NettyAgentClient agentClients) {
		this.agentClients = agentClients;
	}
	public void putIMEventClient(String id , SocketIOClient userClient){
		imClients.putClient(id, userClient);
	}
	
	public void putAgentEventClient(String id , SocketIOClient agentClient){
		agentClients.putClient(id, agentClient);
	}
	
	public void removeIMEventClient(String id , String sessionid){
		imClients.removeClient(id, sessionid);
	}
	
	public void removeAgentEventClient(String id , String sessionid){
		agentClients.removeClient(id, sessionid);
	}
	
	
	
	public void sendIMEventMessage(String id , String event , Object data){
		List<SocketIOClient> userClients = imClients.getClients(id) ;
		for(SocketIOClient userClient : userClients){
			userClient.sendEvent(event, data);
		}
	}
	
	public void sendAgentEventMessage(String id , String event , Object data){
		List<SocketIOClient> agents = agentClients.getClients(id) ;
		for(SocketIOClient agentClient : agents){
			agentClient.sendEvent(event, data);
		}
	}
}
