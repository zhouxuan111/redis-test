package com.zx.redis.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhouxuan
 * @date 2020/8/13 15:06
 */
@Data
@Builder
public class SlowLogFromRedis {

    /**
     * log Id
     */
    private long id;

    /**
     * 发生时间
     */
    private long timeStamp;

    /**
     * 执行时间
     */
    private long executionTime;

    /**
     * 命令参数
     */
    private List<String> args;
}
