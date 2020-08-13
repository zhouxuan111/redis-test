package com.zx.redis.model;

import java.util.Date;

import lombok.Data;

/**
 * redis的慢查询日志
 * @author zhouxuan
 * @date 2020/8/13 11:32
 */
@Data
public class RedisSlowLog {

    /**
     * 主键
     */
    private Long Id;

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 命令耗时 - 毫秒
     */
    private Long excuteTime;

    /**
     * 执行命令
     */
    private String command;

    /**
     * 命令的执行时间
     */
    private Date createTime;
}
