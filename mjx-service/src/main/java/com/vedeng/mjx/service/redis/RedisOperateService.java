package com.vedeng.mjx.service.redis;

import com.vedeng.mjx.common.constant.CommonConstant;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author wayne.liu
 * @description 
 * @date 2019/6/26 13:56
 */
@Service
public class RedisOperateService {

    @Autowired
    private RedissonClient redisson;

    /**
      * 计数用,不设置有效期
      * @author wayne.liu
      * @date  2019/6/26 13:58
      * @param
      * @return +1后的值
     *          0
      */
    public long incrementAndGet(String redisKey){

        RAtomicLong value = redisson.getAtomicLong(redisKey);

        return value == null ? 0L : value.incrementAndGet();

    }

    /**
     * 计数用,设置有效期,
     * 此方法一般用于初始设置一个key的有效期，无需当前返回值的可以忽略
     * @author wayne.liu
     * @date  2019/6/26 13:58
     * @param redisKey key
     * @param expire 毫秒值
     * @return +1后的值
     *          0
     */
    public long incrementAndGetWithExpire(String redisKey,long expire){

        RAtomicLong value = redisson.getAtomicLong(redisKey);

        if(value == null){
            return 0L;
        }

        long current = value.incrementAndGet();

        value.expire(expire,TimeUnit.MILLISECONDS);

        return current;

    }

    /**
     * 返回当前值
     * @author wayne.liu
     * @date  2019/6/26 13:58
     * @param
     * @return 当前值
     *          0
     */
    public long getCurrent(String redisKey){

        RAtomicLong value = redisson.getAtomicLong(redisKey);

        return value == null ? 0L : value.get();

    }


    /**
     * [String结构]设置redisKey的值，不设置有效期
     * @author wayne.liu
     * @date  2019/6/26 13:58
     * @param
     * @return
     *
     */
    public void setKeyValue(String redisKey,String value){

        RBucket<String> key = redisson.getBucket(redisKey);

        key.set(value);

    }

    /**
     * [String结构]设置redisKey的值，设置有效期
     * @author wayne.liu
     * @date  2019/6/26 13:58
     * @param
     * @return
     *
     */
    public void setKeyValueWithExpire(String redisKey,String value,long expire){

        RBucket<String> key = redisson.getBucket(redisKey);

        key.set(value,expire, TimeUnit.MILLISECONDS);

    }

    /**
     * [String结构]获取redisKey内的值
     * @author wayne.liu
     * @date  2019/6/26 13:58
     * @param
     * @return
     *
     */
    public String getKeyValue(String redisKey){

        RBucket<String> key = redisson.getBucket(redisKey);

        return key != null ? key.get() : null;

    }

    /**
      * 删除 Long的key值
      * @author wayne.liu
      * @date  2019/6/27 22:23
      * @param 
      * @return 
      */
    public void deleteLongRedisKey(String redisKey){

        RAtomicLong value = redisson.getAtomicLong(redisKey);

        if(value != null){
            value.delete();
        }
    }

    /**
     * 删除 String的key值
     * @author wayne.liu
     * @date  2019/6/27 22:23
     * @param
     * @return
     */
    public void deleteStringRedisKey(String redisKey){

        RBucket<String> key = redisson.getBucket(redisKey);

        if(key != null){
            key.delete();
        }
    }

    /**
     * 获取值|非0自增, 0的时候设置失效时间
     * @author franlin.wu
     * @param redisKey
     * @param expireIn  失效时间 单位 毫秒
     * @return
     */
    public long getIncrementValue(String redisKey, long expireIn) {
        // 获取初始值
        long val = getCurrent(redisKey);

        return CommonConstant.ZEOR == val ? incrementAndGetWithExpire(redisKey, expireIn) : incrementAndGet(redisKey);
    }

}
