package com.ukefu.service.cache;


public interface CacheInstance {
	/**
	 * 坐席状态
	 * @return
	 */
	public CacheBean getAgentStatusCacheBean() ;
	
	
	/**
	 * 服务中用户
	 * @return
	 */
	public CacheBean getAgentUserCacheBean();
	
	
	/**
	 * 在线用户
	 * @return
	 */
	public CacheBean getOnlineCacheBean();
	
}