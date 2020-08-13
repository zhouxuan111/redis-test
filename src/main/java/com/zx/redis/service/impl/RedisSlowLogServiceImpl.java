package com.zx.redis.service.impl;

import com.zx.redis.mapper.RedisSlowLogMapper;
import com.zx.redis.model.RedisSlowLog;
import com.zx.redis.service.RedisSlowLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouxuan
 * @date 2020/8/13 14:58
 */
@Service
public class RedisSlowLogServiceImpl implements RedisSlowLogService {

    @Autowired
    private RedisSlowLogMapper redisSlowLogMapper;

    @Override
    public void add(RedisSlowLog redisSlowLog) {
        redisSlowLogMapper.add(redisSlowLog);
    }
}
