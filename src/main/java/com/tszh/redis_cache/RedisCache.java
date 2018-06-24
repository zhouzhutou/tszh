package com.tszh.redis_cache;

import java.util.*;

/**
 * Created by Administrator on 2018/6/15 0015.
 */
public interface RedisCache {

    /**
     * 按键值方式存储
     * @param key
     * @param value
     */
    public void put(String key, Object value);

    /**
     * 按键值存储并设置过期时间
     * @param key
     * @param value
     * @param timeout
     */
    public void put(String key, Object value, long timeout);

    /**
     * 获取键对应的值
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 获取键对应的值，并将其转化为指定的类型
     * @param key
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> requiredType);

    /**
     * 删除键值
     * @param key
     */
    public void remove(String key);

    /**
     * 获取匹配指定模式的键的集合
     * @return
     */
    public Set<String> keys(String pattern);

    /**
     * 存储散列类型
     * @param key
     * @param value
     */
    public void putHash(String key, Map<?,?> value);

    /**
     * 以散列的方式存储对象
     * @param key
     * @param field
     * @param value
     */
    public void putHash(String key, String field, Object value);

    /**
     * 获取键对应的散列值
     * @param key
     * @return
     */
    public Map<?,?> getHash(String key);

    /**
     * 以散列方式获取对象的某个属性
     * @param key
     * @param field
     * @return
     */
    public Object getHash(String key, String field);

    /**
     * 以散列方式获取对象的某个属性，并将其转化为指定的类型
     * @param key
     * @param field
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getHash(String key, String field, Class<T> requiredType);

    /**
     * 删除散列键值
     * @param key
     */
    public void removeHash(String key);

    /**
     * 以散列方式删除键对应对象的多个指定属性
     * @param key
     * @param fields
     */
    public void removeHash(String key, Object... fields);

    /**
     * 执行管道存储
     * @param data
     */
    public void put(final Map<String, ?> data);

    /**
     * 以当前系统时间作为分数将元素插入有序集合
     * @param key
     * @param value
     * @return
     */
    public boolean zAdd(String key, Object value);

    /**
     * 以指定分数将元素插入有序集合
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean zAdd(String key, Object value, double score);

    /**
     * 获取键对应的按元素分数从小到大顺序排列的索引从start到end之间的所有元素的集合
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zGetByRange(String key, long start, long end);

    /**
     * 获取键对应的元素分数值在min和max之间（包含min和max）的所有元素的集合
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> zGetByScore(String key, double min, double max);

    /**
     * 删除一个或多个指定元素
     * @param key
     * @param values
     * @return
     */
    public long zRemove(String key, Object... values);

    /**
     * 按照元素分数从小到大的顺序删除索引处在start到end之间的所有元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zRemoveByRange(String key, long start, long end);

    /**
     * 删除元素分数值在min和max之间（包含min和max）的所有元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zRemoveByScore(String key, double min, double max);
}
