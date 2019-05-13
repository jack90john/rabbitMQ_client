package com.jack90john.utils;

import com.jack90john.conf.ConnectionConfig;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * Description: 渠道工具类
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */
public class ConnectionUtil {

    private static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);
    private static Connection connection;
    /**
     * 获取MQ链接渠道
     *
     * @param connectionConfig 链接配置
     * @return MQ链接渠道
     */
    public static Connection getConnectionInstance(ConnectionConfig connectionConfig) {

        //初始化链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory() {{
            setVirtualHost(connectionConfig.getVirtualHost());
            setUsername(connectionConfig.getUsername());
            setPassword(connectionConfig.getPassword());
            setAutomaticRecoveryEnabled(connectionConfig.getAutomaticRecoveryEnabled());
            setNetworkRecoveryInterval(connectionConfig.getNetworkRecoveryInterval());
        }};

        //创建mq链接
        String[] hostArr = connectionConfig.getHost().split(",");
        Address[] allAddress = Arrays.stream(hostArr)
                .map(longHost -> {
                    String[] split = longHost.split(":");
                    if (split.length > 1) {
                        return new Address(split[0], Integer.valueOf(split[1]));
                    } else {
                        return new Address(split[0]);
                    }
                })
                .toArray(Address[]::new);

        try {
            if (connection == null) {
                connection = connectionFactory.newConnection(allAddress);
            }
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取Channel时IO异常, hosts：{}，exception: {}", allAddress, e);
            throw new RuntimeException("获取Channel连接失败");
        } catch (TimeoutException e) {
            logger.error("获取Channel链接超时, hosts：{}，{}", allAddress, e);
            throw new RuntimeException("获取Channel连接失败");
        }
    }
}