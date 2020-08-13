package com.zx.redis.service;

import com.zx.redis.model.RedisSlowLog;

/**
 * @author zhouxuan
 * @date 2020/8/13 14:57
 */
public interface RedisSlowLogService {

    /**
     * 新增
     * @param redisSlowLog
     */
    void add(RedisSlowLog redisSlowLog);
}
