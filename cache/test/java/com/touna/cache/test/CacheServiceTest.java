/**
 * 
 */
package com.wbpm.cache.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wbpm.cache.service.CacheService;

/**
 * @author wuyunjia
 * @createDate 2015-6-4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:applicationContext-bpm.xml" })
public class CacheServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private CacheService cacheService;
	
	@SuppressWarnings("unchecked")
	@Test
	public void cacheHandle() {
		String key = "test";
		String key2 = "test2";
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("123");
		cacheService.put(key, list);
//		list = new ArrayList<String>();
		list.add("1111");
		list.add("2222");
		cacheService.put(key2, list);
		List<String> cacheList = (List)cacheService.get(key);
		for (String str : cacheList) {
			System.out.println(str);
		}
		cacheList = (List)cacheService.get(key2);
		for (String str : cacheList) {
			System.out.println(str);
		}
		cacheService.remove(key);
		cacheList = (List)cacheService.get(key);
		Assert.assertTrue(cacheList == null || cacheList.size() == 0);
		cacheList = (List)cacheService.get(key2);
		Assert.assertTrue(cacheList != null || cacheList.size() > 0);
	}
	
}
