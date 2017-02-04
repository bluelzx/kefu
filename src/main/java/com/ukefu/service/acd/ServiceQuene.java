package com.ukefu.service.acd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import com.corundumstudio.socketio.SocketIONamespace;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.aggregation.Aggregations;
import com.hazelcast.mapreduce.aggregation.Supplier;
import com.hazelcast.query.SqlPredicate;
import com.ukefu.core.UKDataContext;
import com.ukefu.service.cache.CacheHelper;
import com.ukefu.service.quene.AgentStatusOrgiFilter;
import com.ukefu.service.quene.AgentUserOrgiFilter;
import com.ukefu.service.repository.AgentUserRepository;
import com.ukefu.util.client.NettyClients;
import com.ukefu.web.model.AgentReport;
import com.ukefu.web.model.AgentService;
import com.ukefu.web.model.AgentStatus;
import com.ukefu.web.model.AgentUser;
import com.ukefu.web.model.MessageOutContent;

public class ServiceQuene {
	
	/**
	 * 获得 当前服务状态
	 * @param orgi
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static AgentReport getAgentReport(String orgi){
		/**
		 * 统计当前在线的坐席数量
		 */
		AgentReport report = new AgentReport() ;
		IMap agentStatusMap = (IMap<String, Object>) CacheHelper.getAgentStatusCacheBean().getCache() ;
		AgentStatusOrgiFilter filter = new AgentStatusOrgiFilter(orgi) ;
		Long agents = (Long) agentStatusMap.aggregate(Supplier.fromKeyPredicate(filter), Aggregations.count()) ;
		report.setAgents(agents.intValue());
		
		/**
		 * 统计当前服务中的用户数量
		 */
		IMap agentUserMap = (IMap<String, Object>) CacheHelper.getAgentUserCacheBean().getCache() ;
		Long users = (Long) agentUserMap.aggregate(Supplier.fromKeyPredicate(new AgentUserOrgiFilter(orgi , UKDataContext.AgentUserStatusEnum.INSERVICE.toString())), Aggregations.count()) ;
		report.setUsers(users.intValue());
		
		Long queneUsers = (Long) agentUserMap.aggregate(Supplier.fromKeyPredicate(new AgentUserOrgiFilter(orgi , UKDataContext.AgentUserStatusEnum.INQUENE.toString())), Aggregations.count()) ;
		report.setInquene(queneUsers.intValue());
		
		return report;
	}
	
	public static int getQueneIndex(String userid , String orgi , long ordertime){
		
//		IList<AgentUser> queneUserList = (IList<AgentUser>) CacheHelper.getQueneUserCacheBean().getCache() ;
		int queneUsers = 0 ;
//		for(AgentUser agentUser : queneUserList){
//			if(agentUser.getOrgi().equals(orgi) && agentUser.getUserid().equals(userid)){
//				queneUsers ++ ;
//			}
//		}
		
		return queneUsers;
	}
	/**
	 * 为坐席批量分配用户
	 * @param agentStatus
	 */
	@SuppressWarnings("unchecked")
	public static void allotAgent(AgentStatus agentStatus , String orgi){
		List<AgentUser> agentStatusList = new ArrayList<AgentUser>();
		
		agentStatusList.addAll(((IMap<String , AgentUser>) CacheHelper.getAgentUserCacheBean().getCache()).values( new SqlPredicate( "status = inquene AND orgi = " + orgi ) )) ;
		for(AgentUser agentUser : agentStatusList){
			if(agentStatus != null && agentStatus.getUsers() < 10){
				try{
					AgentService agentService = processAgentService(agentStatus, agentUser, orgi) ;
	
					MessageOutContent outMessage = new MessageOutContent() ;
					outMessage.setMessage(ServiceQuene.getSuccessMessage(agentService));
					outMessage.setMessageType(UKDataContext.MessageTypeEnum.MESSAGE.toString());
					outMessage.setCalltype(UKDataContext.CallTypeEnum.IN.toString());
	
					NettyClients.getInstance().sendIMEventMessage(agentUser.getUserid(), UKDataContext.MessageTypeEnum.STATUS.toString(), outMessage);
	
					NettyClients.getInstance().sendAgentEventMessage(agentService.getAgentid(), UKDataContext.MessageTypeEnum.NEW.toString(), agentUser);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				break ;	//到达 最大用户数
			}
		}
		publishMessage(orgi);
	}
	
	/**
	 * 为坐席批量分配用户
	 * @param agentStatus
	 * @throws Exception 
	 */
	public static void serviceFinish(AgentUser agentUser , String orgi) throws Exception{
		if(agentUser!=null){
			AgentStatus agentStatus = null;
			if(UKDataContext.AgentUserStatusEnum.INSERVICE.toString().equals(agentUser.getStatus()) && agentUser.getAgentno()!=null){
				agentStatus = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(agentUser.getAgentno(), orgi) ;
				if(agentStatus!=null){
					updateAgentStatus(agentStatus  , agentUser , orgi , false) ;
				}
			}
			CacheHelper.getAgentUserCacheBean().delete(agentUser.getUserid(), orgi);
			
			AgentUserRepository agentUserRepository = UKDataContext.getContext().getBean(AgentUserRepository.class) ;
			
			AgentUser agentUseDataBean = agentUserRepository.findById(agentUser.getId()) ;
			
			if(agentUseDataBean!=null){
				agentUseDataBean.setStatus(UKDataContext.AgentUserStatusEnum.END.toString()) ;
				if(agentUser.getServicetime()!=null){
					agentUseDataBean.setSessiontimes(System.currentTimeMillis() - agentUser.getServicetime().getTime()) ;
				}
				
				agentUserRepository.save(agentUseDataBean) ;
			}
			
			if(agentStatus!=null){
				NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), UKDataContext.MessageTypeEnum.END.toString(), agentUser);
			}
			
			publishMessage(orgi);
		}
	}
	
	/**
	 * 更新坐席当前服务中的用户状态，需要分布式锁
	 * @param agentStatus
	 * @param agentUser
	 * @param orgi
	 */
	private static void updateAgentStatus(AgentStatus agentStatus , AgentUser agentUser , String orgi , boolean in){
		Lock lock = CacheHelper.getAgentStatusCacheBean().getLock("LOCK", orgi) ;
		lock.lock();
		try{
			if( in == false && agentStatus.getUsers()>0){
				agentStatus.setUsers(agentStatus.getUsers() - 1);
			}else if(in = true){
				agentStatus.setUsers(agentStatus.getUsers() + 1);
			}
			CacheHelper.getAgentStatusCacheBean().put(agentStatus.getId(), agentStatus, orgi);
			
		}finally{
			lock.unlock();
		}
	}
	
	public static void publishMessage(String orgi){
		/**
		 * 坐席状态改变，通知监测服务
		 */
		UKDataContext.getContext().getBean("agentNamespace" , SocketIONamespace.class) .getBroadcastOperations().sendEvent("status", ServiceQuene.getAgentReport(orgi));
	}
	/**
	 * 为用户分配坐席
	 * @param agentUser
	 */
	@SuppressWarnings("unchecked")
	public static AgentService allotAgent(AgentUser agentUser , String orgi){
		/**
		 * 查询条件，当前在线的 坐席，并且 未达到最大 服务人数的坐席
		 */
		
		List<AgentStatus> agentStatusList = new ArrayList<AgentStatus>();
		agentStatusList.addAll(((IMap<String , AgentStatus>) CacheHelper.getAgentStatusCacheBean().getCache()).values( new SqlPredicate( "users < 10" ) )) ;
		AgentService agentService = null ;	//放入缓存的对象
		try {
			agentService = processAgentService(agentStatusList.size()>0 ? agentStatusList.get(0) : null, agentUser, orgi) ;
		}catch(Exception ex){
			ex.printStackTrace(); 
		}
		publishMessage(orgi);
		return agentService;
	}
	
	private static AgentService processAgentService(AgentStatus agentStatus , AgentUser agentUser , String orgi) throws Exception{
		AgentService agentService = new AgentService();	//放入缓存的对象
		agentService.setOrgi(orgi);

		if(agentStatus!=null){
			agentService.setAgentno(agentStatus.getUsername());
			agentService.setAgentid(agentStatus.getUserid());	//agent
			agentUser.setAgentserviceid(agentStatus.getAgentserviceid());
			updateAgentStatus(agentStatus  , agentUser , orgi , true) ;
			
			long waittingtime = 0  ;
			if(agentUser.getWaittingtimestart()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getWaittingtimestart().getTime() ;
			}else if(agentUser.getCreatetime()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getCreatetime().getTime() ;
			}
			agentUser.setWaittingtime((int)waittingtime);
			
			agentUser.setServicetime(new Date());
			
			agentUser.setStatus(UKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentService.setStatus(UKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentService.setTimes(agentUser.getCreatetime().getTime());
			
			agentUser.setAgentno(agentService.getAgentid());
		}else{
			agentUser.setStatus(UKDataContext.AgentUserStatusEnum.INQUENE.toString());
			agentService.setStatus(UKDataContext.AgentUserStatusEnum.INQUENE.toString());
			agentService.setTimes(agentUser.getCreatetime().getTime());
		}

		/**
		 * 分配成功以后， 将用户 和坐席的对应关系放入到 缓存
		 */
		/**
		 * 将 AgentUser 放入到 当前坐席的 服务队列
		 */
		agentUser.setLogindate(new Date());
		
		AgentUserRepository agentUserRepository = UKDataContext.getContext().getBean(AgentUserRepository.class) ;
		
		if(CacheHelper.getAgentUserCacheBean().getCacheObject(agentUser.getUserid(), UKDataContext.SYSTEM_ORGI) == null){
			agentUserRepository.save(agentUser);
		}else{
			AgentUser agentUseDataBean = agentUserRepository.findById(agentUser.getId()) ;
			if(agentUseDataBean!=null){
				agentUseDataBean.setAgentno(agentService.getAgentid()) ;
				agentUseDataBean.setStatus(agentUser.getStatus()) ;
				
				agentUserRepository.save(agentUseDataBean) ;
			}
		}

		CacheHelper.getAgentUserCacheBean().put(agentUser.getUserid(), agentUser , UKDataContext.SYSTEM_ORGI) ;

		agentService.setAgentUser(agentUser);

		//			RivuTools.publishMessage("/"+orgi+"/com.system.status", ServiceQuene.getAgentReport(orgi));

		return agentService ;
	}
	/**
	 * 
	 * @param agentStatus
	 * @return
	 */
	public static String getSuccessMessage(AgentService agentService){
		return "坐席分配成功， <span id='agentno'>"+agentService.getAgentno()+"</span>为您服务。" ;
	}
	
	public static String getNoAgentMessage(int queneIndex){
		return "坐席全忙，已进入等待队列，在您之前，还有 <span id='queneindex'>"+queneIndex+"</span> 位等待用户。" ;
	}
	public static String getQueneMessage(int queneIndex){
		return "正在排队，请稍候,在您之前，还有  <span id='queneindex'>"+queneIndex+"</span> 位等待用户。" ;
	}
}
