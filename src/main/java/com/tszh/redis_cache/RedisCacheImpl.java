package com.tszh.redis_cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/15 0015.
 */
@Component("redisCache")
public class RedisCacheImpl implements RedisCache {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCacheImpl() {
        super();
    }

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void put(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> requiredType) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public void putHash(String key, Map<?, ?> value) {
        redisTemplate.opsForHash().putAll(key,value);
    }

    @Override
    public void putHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key,field,value);
    }

    @Override
    public Map<?, ?> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key,field);
    }

    @Override
    public <T> T getHash(String key, String field, Class<T> requiredType) {
        return (T)redisTemplate.opsForHash().get(key,field);
    }

    @Override
    public void removeHash(String key) {
        redisTemplate.opsForHash().delete(key);
    }

    @Override
    public void removeHash(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key,fields);
    }

    @Override
    public void put(Map<String, ?> data) {
        RedisCallback<Map<String,?>> redisCallback=new RedisCallback<Map<String, ?>>() {
            @Override
            public Map<String, ?> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                RedisSerializer<String> rs=new StringRedisSerializer();
                SerializingConverter sc=new SerializingConverter();
                for(String key:data.keySet()){
                    redisConnection.append(rs.serialize(key),sc.convert(data.get(key)));
                }
                redisConnection.closePipeline();
                return null;
            }
        };
        redisTemplate.execute(redisCallback);

    }

    @Override
    public boolean zAdd(String key, Object value)
    {
        return this.zAdd(key,value,System.currentTimeMillis());
    }

    @Override
    public boolean zAdd(String key, Object value, double score)
    {
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public Set<Object> zGetByRange(String key, long start, long end)
    {
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    @Override
    public Set<Object> zGetByScore(String key, double min, double max)
    {
        return redisTemplate.opsForZSet().rangeByScore(key,min,max);
    }

    @Override
    public long zRemove(String key, Object... values)
    {
        return redisTemplate.opsForZSet().remove(key,values);
    }

    @Override
    public long zRemoveByRange(String key, long start, long end)
    {
        return redisTemplate.opsForZSet().removeRange(key,start,end);
    }

    @Override
    public long zRemoveByScore(String key, double min, double max)
    {
        return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
    }
}
