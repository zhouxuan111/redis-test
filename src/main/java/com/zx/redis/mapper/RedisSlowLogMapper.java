package com.zx.redis.mapper;

import com.zx.redis.model.RedisSlowLog;

/**
 * @author zhouxuan
 * @date 2020/8/13 11:36
 */
public interface RedisSlowLogMapper {

    void add(RedisSlowLog redisSlowLog);
}
