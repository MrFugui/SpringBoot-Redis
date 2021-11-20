package com.wangfugui.apprentice.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils:redis工具类
 */
@Component
public class RedisUtils {

    @Autowired
    @Qualifier("getRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("getStringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置键值
     *
     * @Param: [key, value]
     * @return: boolean
     * @Author: MaSiyi
     * @Date: 2021/11/20
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置键值(string)
     *
     * @Param: [key, value]
     * @return: boolean
     * @Author: MaSiyi
     * @Date: 2021/11/20
     */
    public boolean setStr(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 设置键值(string)
     *
     * @Param: [key, value]
     * @return: boolean
     * @Author: MaSiyi
     * @Date: 2021/11/20
     */
    public boolean setStrEx(final String key, String value,long timeout, TimeUnit unit) {
        boolean result = false;
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置失效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setEx(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置key-value
     * <p>
     * 注: 若已存在相同的key, 那么原来的key-value会被丢弃
     *
     * @param key     key
     * @param value   key对应的value
     * @param timeout 过时时长
     * @param unit    timeout的单位
     * @date 2020/3/8 15:40:59
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存当中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key，获取到对应的value值
     *
     * @param key key-value对应的key
     * @return 该key对应的值。
     * 注: 若key不存在， 则返回null。
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key，获取到对应的value(string)值
     *
     * @param key key-value对应的key
     * @return 该key对应的值。
     * 注: 若key不存在， 则返回null。
     */
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


}