package com.ukefu.util.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.ukefu.core.UKDataContext;
import com.ukefu.util.server.handler.AgentEventHandler;
import com.ukefu.util.server.handler.IMEventHandler;
  
@Component  
public class ServerRunner implements CommandLineRunner {  
    private final SocketIOServer server;
    private final SocketIONamespace imSocketNameSpace ;
    private final SocketIONamespace agentSocketIONameSpace ;
    
    @Autowired  
    public ServerRunner(SocketIOServer server) {  
        this.server = server;  
        imSocketNameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.IM.getNamespace())  ;
        agentSocketIONameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.AGENT.getNamespace()) ;
    }
    
    @Bean(name="imNamespace")
    public SocketIONamespace getIMSocketIONameSpace(SocketIOServer server ){
    	imSocketNameSpace.addListeners(new IMEventHandler(server));
    	return imSocketNameSpace  ;
    }
    
    @Bean(name="agentNamespace")
    public SocketIONamespace getAgentSocketIONameSpace(SocketIOServer server){
    	agentSocketIONameSpace.addListeners(new AgentEventHandler(server));
    	return agentSocketIONameSpace;
    }

    public void run(String... args) throws Exception { 
        server.start();  
    }  
}  