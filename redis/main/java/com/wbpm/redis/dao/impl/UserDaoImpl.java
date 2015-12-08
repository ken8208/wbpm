/**
 * Aug 29, 2012
 */
package com.wbpm.redis.dao.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.wbpm.redis.dao.UserDao;
import com.wbpm.redis.domain.User;

/**
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public void save(final User user) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(
						redisTemplate.getStringSerializer().serialize(
								"user.uid." + user.getUid()),
						redisTemplate.getStringSerializer().serialize(
								user.getAddress()));
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wbpm.redis.dao.UserDao#read(java.lang.String)
	 */
	public User read(final String uid) {
		return redisTemplate.execute(new RedisCallback<User>() {

			public User doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(
						"user.uid." + uid);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String address = redisTemplate.getStringSerializer()
							.deserialize(value);
					User user = new User();
					user.setAddress(address);
					user.setUid(uid);
					return user;
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wbpm.redis.dao.UserDao#delete(java.lang.String)
	 */
	public void delete(final String uid) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				connection.del(redisTemplate.getStringSerializer().serialize(
						"user.uid." + uid));
				return null;
			}
		});
	}
}
