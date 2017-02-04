package com.ukefu.service.cache;

import com.ukefu.service.cache.hazelcast.HazlcastCacheHelper;

public class CacheHelper {
	private static CacheHelper instance = new CacheHelper();
	
	/**
	 * 获取缓存实例
	 */
	public static CacheHelper getInstance(){
		return instance ;
	}
	private static CacheInstance cacheInstance = new HazlcastCacheHelper();
	
	public static CacheBean getAgentStatusCacheBean() {
		return cacheInstance.getAgentStatusCacheBean();
	}
	public static CacheBean getAgentUserCacheBean() {
		return cacheInstance.getAgentUserCacheBean();
	}
	public static CacheBean getOnlineUserCacheBean() {
		return cacheInstance.getOnlineCacheBean() ;
	}
	
}
