package com.ukefu.service.cache.hazelcast;

import com.ukefu.core.UKDataContext;
import com.ukefu.service.cache.CacheBean;
import com.ukefu.service.cache.CacheInstance;
/**
 * Hazlcast缓存处理实例类
 * @author admin
 *
 */
public class HazlcastCacheHelper implements CacheInstance{
	/**
	 * 服务类型枚举
	 * @author admin
	 *
	 */
	public enum CacheServiceEnum{
		HAZLCAST_CLUSTER_AGENT_USER_CACHE, HAZLCAST_CLUSTER_AGENT_STATUS_CACHE, HAZLCAST_CLUSTER_QUENE_USER_CACHE,HAZLCAST_ONLINE_CACHE;
		public String toString(){
			return super.toString().toLowerCase();
		}
	}
	
	public static HazlcastDistributedCache getHazlcastCache(){
		return UKDataContext.getContext().getBean(HazlcastDistributedCache.class) ;
	}
	@Override
	public CacheBean getAgentStatusCacheBean() {
		// TODO Auto-generated method stub
		return getHazlcastCache().getCacheInstance(CacheServiceEnum.HAZLCAST_CLUSTER_AGENT_STATUS_CACHE.toString()) ;
	}
	@Override
	public CacheBean getAgentUserCacheBean() {
		// TODO Auto-generated method stub
		return getHazlcastCache().getCacheInstance(CacheServiceEnum.HAZLCAST_CLUSTER_QUENE_USER_CACHE.toString()) ;
	}
	@Override
	public CacheBean getOnlineCacheBean() {
		return getHazlcastCache().getCacheInstance(CacheServiceEnum.HAZLCAST_ONLINE_CACHE.toString()) ;
	}
}
