package com.jack90john.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description: 链接配置类
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@Data
@Component
public class ConnectionConfig {

    @Value("${connectionConfig.host:localhost:5672}")
    private String host;                            //主机地址（默认：localhost:5672）
    @Value("${connectionConfig.virtualHost:/}")
    private String virtualHost;                     //虚拟消息服务器（默认：/）
    @Value("${connectionConfig.username:guest}")
    private String username;                        //用户名（默认：guest）
    @Value("${connectionConfig.password:guest}")
    private String password;                        //密码（默认：guest）
    @Value("${connectionConfig.automaticRecoveryEnabled:true}")
    private Boolean automaticRecoveryEnabled;       //网络异常重连（默认：true）
    @Value("${connectionConfig.networkRecoveryInterval:5000}")
    private Integer networkRecoveryInterval;        //重连间隔单位ms（默认：5000，即每5秒重试一次）

}
