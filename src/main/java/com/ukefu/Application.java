package com.ukefu;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.ukefu.core.UKDataContext;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories("com.ukefu.service.repository")
public class Application {
	
    @Value("${uk.im.server.host}")  
    private String host;  
  
    @Value("${uk.im.server.port}")  
    private Integer port; 
    
	
    @Bean  
    public SocketIOServer socketIOServer()   
    {  

    	Configuration config = new Configuration();
//		config.setHostname("localhost");
		config.setPort(port);
		config.setSocketConfig(new SocketConfig());
//		config.setOrigin("http://im.uckefu.com");
		
		config.setWorkerThreads(100);
//		config.setStoreFactory(new HazelcastStoreFactory());
		config.setAuthorizationListener(new AuthorizationListener() {
			
			public boolean isAuthorized(HandshakeData data) {
				return true;
			}
		});
		
        return new SocketIOServer(config);  
    } 
    
    @Bean(name="webimport") 
    public Integer getWebIMPort() {   
    	UKDataContext.setWebIMPort(port);
    	return port;   
    }   
    
    @Bean   
    public MultipartConfigElement multipartConfigElement() {   
            MultipartConfigFactory factory = new MultipartConfigFactory();  
            factory.setMaxFileSize("50MB"); //KB,MB  
            factory.setMaxRequestSize("100MB");   
            return factory.createMultipartConfig();   
    }   
      
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error = new ErrorPage("/error.html");

                container.addErrorPages(error);
            }
        };
    }
    
    @Bean  
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {  
        return new SpringAnnotationScanner(socketServer);  
    }  

	public static void main(String[] args) {
		UKDataContext.setApplicationContext(SpringApplication.run(Application.class, args));
	}

}
