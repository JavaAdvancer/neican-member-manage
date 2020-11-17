package com.jiyou.nm.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 作者： 张恒同
 * 时间： 2018/4/15   09:11
 * 描述： redis工具类 - 目前只针对String进行操作
 */
@Component
public class RedisUtil {
    private Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;


    /**** String部分 *****/

    /**
     * 检查是否存在相应的Key
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:06
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 存储值/更新值
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:06
     * @param key value
     * @return
     */
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取key对应的value
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:10
     * @param key
     * @return
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取key对应的字符集
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:20
     * @param
     * @return
     */
    public String getRange(String key, long start, long end){
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 获取key的原值，并将新值进行赋值
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:53
     * @param   key value
     * @return
     */
    public Object getAndSet(String key, String value){
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 只有key不存在时 进行赋值
     *
     * @author 张恒同
     * @date 2018/4/15 上午10:58
     * @param
     * @return key之前存在返回false，未存在返回true
     */
    public boolean setIfAbent(String key, String value){
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 删除指定key
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:26
     * @param
     * @return
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除keys
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:27
     * @param
     * @return
     */
    public void deleteKeys(Collection<String> keys){
        redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:29
     * @param
     * @return
     */
    public void expire(String key, long timeout, TimeUnit timeUnit){
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 指定过期时间
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:29
     * @param
     * @return
     */
    public void expireAt(String key, Date date){
        redisTemplate.expireAt(key, date);
    }

    /**
     * 返回key的剩余过期时间
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:31
     * @param
     * @return
     */
    public long getExpire(String key, TimeUnit timeUnit){
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 修改key的名称
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:31
     * @param
     * @return
     */
    public void rename(String oldKey, String newKey){
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅且当oldKey存在时，才修改oldKey的名称
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:33
     * @param
     * @return
     */
    public boolean renameIfAbent(String oldKey, String newKey){
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回key 存储的数据类型
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:36
     * @param
     * @return
     */
    public DataType type(String key){
        return redisTemplate.type(key);
    }

    /**
     * 获取key对应value字符串长度
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:40
     * @param
     * @return
     */
    public long size(String key){
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:43
     * @param
     * @return
     */
    public void multiset(Map<String, String> map){
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 批量添加 当指定key不存在时指定
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:44
     * @param
     * @return
     */
    public boolean multisetIfAbent(Map<String, String> map){
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 自增
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:48
     * @param
     * @return
     */
    public Long incrBy(String key, Long increment){
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 浮点自增
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:48
     * @param
     * @return
     */
    public Double incrByDouble(String key, Double increment){
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加值至末尾
     *
     * @author 张恒同
     * @date 2018/4/16 下午1:49
     * @param
     * @return
     */
    public Integer append(String key, String value){
        return redisTemplate.opsForValue().append(key, value);
    }

    /****** String end *******/

    /****** Hash start *******/

    /**
     * 获取key对应的所有键值对
     *
     * @param key
     * @return
     * */
    public Map<Object, Object> hashmGet(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置hash
     *
     * @param key
     * @param map
     * */
    public boolean hashmSet(String key, Map<Object, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash, 并设置有效期
     *
     * @param key
     * @param map
     * @param time 秒
     * */
    public boolean hashmSet(String key, Map<Object, Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0){expire(key, time, TimeUnit.SECONDS);}
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash
     *
     * @param key
     * @param item
     * @param value
     * */
    public boolean hashSet(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash
     *
     * @param key
     * @param item
     * @param value
     * */
    public boolean hashSet(String key, String item, Object value, long time){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0){expire(key, time, TimeUnit.DAYS);}
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash
     *
     * @param key
     * @param item
     * */
    public Integer hashGet(String key, String item){
        try {
            Object result = redisTemplate.opsForHash().get(key, item);
            if (result == null){
                return 0;
            } else {
                return (Integer) result;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置hash
     *
     * @param key
     * @param item
     * */
    public Long hashSetIncr(String key, String item, long value){
        try {
            return redisTemplate.opsForHash().increment(key, item, value);
        }catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 获取指定key的所有字段
     *
     * @param key
     * */
    public Set<String> hashKeys(String key){
        try {
            Set<String> keySets = redisTemplate.opsForHash().keys(key);
            return keySets;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String key, String value, long time){
        try {
            set(key, value);
            if (time > 0){expire(key, time, TimeUnit.SECONDS);}
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
