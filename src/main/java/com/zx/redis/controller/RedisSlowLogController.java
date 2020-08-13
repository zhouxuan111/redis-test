package com.zx.redis.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.zx.redis.model.RedisSlowLog;
import com.zx.redis.model.SlowLogFromRedis;
import com.zx.redis.service.RedisSlowLogService;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouxuan
 * @date 2020/8/13 15:13
 */
@RestController
public class RedisSlowLogController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisSlowLogService redisSlowLogService;

    @GetMapping("test")
    public void test() {

        List<Object> list = redisTemplate.execute(new RedisCallback<List<Object>>() {

            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {

                RedisAsyncCommands redisAsyncCommands = (RedisAsyncCommands) connection.getNativeConnection();
                RedisFuture<List<Object>> future = redisAsyncCommands.slowlogGet(12);
                try {
                    System.out.println(redisAsyncCommands.slowlogLen().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                try {
                    return future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
        //插入数据库
        List<SlowLogFromRedis> result = list.stream().map(e -> from(e)).collect(Collectors.toList());
        for (SlowLogFromRedis s : result) {
            RedisSlowLog redisSlowLog = new RedisSlowLog();
            redisSlowLog.setLogId(s.getId());
            redisSlowLog.setExcuteTime(s.getExecutionTime());
            redisSlowLog.setCreateTime(new Date(s.getTimeStamp()));
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : s.getArgs()) {
                stringBuilder.append(arg).append(" ");
            }
            redisSlowLog.setCommand(stringBuilder.toString());
            redisSlowLogService.add(redisSlowLog);
        }
    }

    public SlowLogFromRedis from(Object object) {
        List data = (List) object;
        List<byte[]> args = (List<byte[]>) data.get(3);
        SlowLogFromRedis slowlog = SlowLogFromRedis.builder().id(Long.parseLong(data.get(0).toString()))
                .timeStamp(Long.parseLong(data.get(1).toString())).executionTime(Long.parseLong(data.get(2).toString()))
                .args(args.stream().map(e -> new String(e)).collect(Collectors.toList())).build();
        return slowlog;
    }
}
