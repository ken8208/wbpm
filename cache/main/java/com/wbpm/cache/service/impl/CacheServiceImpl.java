package com.wbpm.cache.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.wbpm.cache.core.CacheManager;
import com.wbpm.cache.core.CacheManagerFactory;
import com.wbpm.cache.service.CacheService;
import com.wbpm.core.exception.ServiceException;
import com.wbpm.core.utils.PropertiesLoaderUtils;

/**
 * 缓存服务接口实现类
 * @author wuyunjia
 * @createDate 2015-6-4
 */
@Service
public class CacheServiceImpl implements CacheService, InitializingBean, DisposableBean {
	
	private final Log log = LogFactory.getLog(getClass());
	
	private static final String CACHE_CONFIG = "cache.properties";
	private static final String CACHE_MANAGER_CLASS_KEY = "cache.manager.class";
	
	private CacheManager cacheManager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils.loadAllProperties(CACHE_CONFIG);
		} catch (IOException e) {
			log.error("找不到缓存配置属性文件["+CACHE_CONFIG+"]");
			throw new ServiceException("找不到缓存配置属性文件");
		}
		
		String cacheManagerClass = properties.getProperty(CACHE_MANAGER_CLASS_KEY);
		if (StringUtils.isEmpty(cacheManagerClass)) {
			log.error("属性文件["+CACHE_CONFIG+"]中的'cache.manager.class'未设置相应的类路径");
			throw new ServiceException("属性文件中未设置缓存管理实现类");
		}
		cacheManager = CacheManagerFactory.getTounaCacheManager(cacheManagerClass);
		
	}
	
	@Override
	public void destroy() throws Exception {
		cacheManager.destroy();
	}

	@Override
	public void put(String key, Object value) {
		cacheManager.getCache().put(key, value);
	}

	@Override
	public Object get(String key) {
		return cacheManager.getCache().get(key);
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache().remove(key);
	}

}
