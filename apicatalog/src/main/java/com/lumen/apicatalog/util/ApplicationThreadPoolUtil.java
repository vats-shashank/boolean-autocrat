package com.lumen.apicatalog.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ApplicationThreadPoolUtil {
	
	
	private static ExecutorService threadPoolExecutor=null;
	private static ExecutorService cacheThreadPoolExecutor=null;
	private static String threadPoolCountFromDb = null;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationThreadPoolUtil.class);
	private String THREAD_COUNT = "10";
	
	public ExecutorService getExecutorServiceInstance(){
			if(threadPoolCountFromDb == null){
				threadPoolCountFromDb = getThreadCount();
				return initializeFixedOrCacheThreadPoolExecutorService(threadPoolCountFromDb);
			}else{
				return reInitializeUpdatedThreadPoolCount();
			}
	}
	/**
	 * 
	 * @return
	 */
	private ExecutorService reInitializeUpdatedThreadPoolCount(){
		String updatedThreadPoolCountFromDb = null;
		try{
			updatedThreadPoolCountFromDb = getThreadCount();
			if(updatedThreadPoolCountFromDb!=null && Integer.parseInt(updatedThreadPoolCountFromDb)>0 && Integer.parseInt(threadPoolCountFromDb) != Integer.parseInt(updatedThreadPoolCountFromDb)){
				logger.info("Therad pool count is changed from : "+threadPoolCountFromDb +" to : "+updatedThreadPoolCountFromDb);
				threadPoolCountFromDb = updatedThreadPoolCountFromDb;
				if(threadPoolExecutor!=null){
					threadPoolExecutor.shutdown();
				}
			}
			if(threadPoolExecutor!=null && threadPoolExecutor.isShutdown()){
					if(!threadPoolExecutor.isTerminated()){
						return initializeCacheThreadPoolExecutorService();
					}else{
						threadPoolExecutor = shutDownExistingPoolAndStartNewPool();
					}
				}
			if(cacheThreadPoolExecutor!=null && cacheThreadPoolExecutor.isShutdown()){
				if(cacheThreadPoolExecutor.isTerminated()){
					cacheThreadPoolExecutor=null;
				}
			}
			return threadPoolExecutor; 
		}catch(Exception e){
			logger.info("Errror while reInitializeUpdatedThreadPoolCount : "+e);
		}
		return threadPoolExecutor; 
	}
	
	
	private ExecutorService shutDownExistingPoolAndStartNewPool(){
		threadPoolExecutor=null;
		if(cacheThreadPoolExecutor!=null){
			cacheThreadPoolExecutor.shutdown();
		}
		return initializeFixedOrCacheThreadPoolExecutorService(threadPoolCountFromDb);
	}
	
	private ExecutorService initializeCacheThreadPoolExecutorService() {
		if (cacheThreadPoolExecutor != null) {
			return cacheThreadPoolExecutor;
		} else {
			cacheThreadPoolExecutor = Executors.newCachedThreadPool();
			return cacheThreadPoolExecutor;
		}
	}
	
	private ExecutorService initializeFixedOrCacheThreadPoolExecutorService(String threadPoolCountFromDb) {
		try{
			if(threadPoolExecutor==null){
				if(threadPoolCountFromDb!=null){
					Integer threadPoolSize = Integer.parseInt(threadPoolCountFromDb);
					if(threadPoolSize<=0){
						threadPoolExecutor=Executors.newCachedThreadPool();
					}else{
						threadPoolExecutor=Executors.newFixedThreadPool(threadPoolSize);
					}
				}else{
					threadPoolExecutor=Executors.newCachedThreadPool();
				}
			}
		}
		catch (Exception e) {
			logger.error("Error while initializeFixedOrCacheThreadPoolExecutorService" +e);
			threadPoolExecutor=Executors.newCachedThreadPool();
		}
		return threadPoolExecutor;
	}
	
	private String getThreadCount(){
		try{
			return THREAD_COUNT;
		}catch(Exception e){
			logger.error("Exception while getting thread count : "+e);
			return null;
		}
	}
}
