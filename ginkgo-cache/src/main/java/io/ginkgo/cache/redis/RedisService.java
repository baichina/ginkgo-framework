package io.ginkgo.cache.redis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import io.ginkgo.cache.exception.GinkgoCacheErrorCode;
import io.ginkgo.cache.exception.GinkgoCacheException;

/**
 * redis服务
 * 
 * @since 1.0.0
 * @author Barry
 */
@Service
public class RedisService<T> {

	private static final Logger log = Logger.getLogger(RedisService.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, T> redisTemplate;

	private static final JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();

	/**
	 * 设置字符串
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            过期时间（秒）
	 */
	public void set(String key, String value, long timeout) {
		try {
			stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置字符串，永不过期
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		try {
			stringRedisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置字符串，如果不存在
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            过期时间（秒）
	 */
	public boolean setnx(String key, String value, long timeout) {
		boolean result = false;
		try {
			result = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
			if (result) {
				// 如果操作失败，会导致问题
				expire(key, timeout);
			}
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
		return result;
	}

	/**
	 * 获取字符串
	 * 
	 * @param key
	 * @return 字符串
	 */
	public String get(String key) {
		try {
			return stringRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置对象
	 * 
	 * @param key
	 * @param obj
	 * @param timeout
	 *            过期时间（秒）
	 */
	public void setObj(String key, T obj, long timeout) {
		try {
			redisTemplate.setValueSerializer(redisSerializer);
			redisTemplate.opsForValue().set(key, obj, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置对象，永不过期
	 * 
	 * @param key
	 * @param obj
	 */
	public void setObj(String key, T obj) {
		try {
			redisTemplate.setValueSerializer(redisSerializer);
			redisTemplate.opsForValue().set(key, obj);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param key
	 * @return 字符串
	 */
	public T getObj(String key) {
		try {
			redisTemplate.setValueSerializer(redisSerializer);
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置hash
	 *
	 * @param key
	 * @param hash
	 */
	public void putAll(String key, Map<Object, Object> hash, long timeout) {
		try {
			stringRedisTemplate.opsForHash().putAll(key, hash);
			expire(key, timeout);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置hash，永不过期
	 *
	 * @param key
	 * @param hash
	 */
	public void putAll(String key, Map<Object, Object> hash) {
		try {
			stringRedisTemplate.opsForHash().putAll(key, hash);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 获取hash
	 *
	 * @param key
	 */
	public Map<Object, Object> entries(String key) {
		try {
			return stringRedisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置hash值
	 * 
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 * @param timeout
	 */
	public void put(String key, Object hashKey, Object hashValue, long timeout) {
		try {
			stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
			expire(key, timeout);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置hash值，永不过期
	 * 
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 */
	public void put(String key, Object hashKey, Object hashValue) {
		try {
			stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 获取hash值
	 * 
	 * @param key
	 * @param hashKey
	 */
	public Object getHash(String key, Object hashKey) {
		try {
			return stringRedisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 删除hashKey
	 * 
	 * @param key
	 * @param hashKeys
	 */
	public void delHash(String key, Object... hashKeys) {
		try {
			stringRedisTemplate.opsForHash().delete(key, hashKeys);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 */
	public void del(String key) {
		try {
			stringRedisTemplate.delete(key);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 */
	public boolean expire(String key, long timeout) {
		try {
			stringRedisTemplate.hasKey(key);
			return stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		try {
			return stringRedisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 获取当前目录下所有的key
	 * 
	 * @param head
	 * @return
	 */
	public Set<String> getAllKey(String head) {
		try {
			return stringRedisTemplate.keys(head + "*");
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 删除当前目录下所有的key
	 * 
	 * @param head
	 *            (如head=as:token删除所有的token缓存)
	 * @return
	 */
	public void delAllKey(String head) {
		try {
			Set<String> keys = getAllKey(head);
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				// 删除key
				del(key);
			}
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}

	/**
	 * 自增+1
	 * 
	 * @param key
	 * @param timeout
	 *            超时时间
	 */
	public long incr(String key, long timeout) {
		return incrBy(key, 1, timeout);
	}

	/**
	 * 自增+N
	 * 
	 * @param key
	 * @param delta
	 *            增量
	 * @param timeout
	 *            超时时间
	 */
	public long incrBy(String key, long delta, long timeout) {
		try {
			long times = stringRedisTemplate.opsForValue().increment(key, delta);
			expire(key, timeout);
			return times;
		} catch (Exception e) {
			log.error(e);
			throw new GinkgoCacheException(GinkgoCacheErrorCode.REDIS_EXCEPTION.getCode(),
					GinkgoCacheErrorCode.REDIS_EXCEPTION.getMsg());
		}
	}
}