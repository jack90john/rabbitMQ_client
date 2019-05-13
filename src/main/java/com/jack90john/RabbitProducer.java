package com.jack90john;

import com.jack90john.conf.ConnectionConfig;
import com.jack90john.conf.ExchangeConfig;
import com.jack90john.constant.ExchangeName;
import com.jack90john.utils.ConnectionUtil;
import com.jack90john.utils.SerializeUtil;
import com.jack90john.utils.SpringBeanUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Description: 消息生产者
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@Slf4j
public class RabbitProducer {

    private static RabbitProducer rabbitProducer;

    private Channel channel;

    private RabbitProducer() {
    }

    /**
     * 消息发布
     *
     * @param builtinExchangeType        发送方式
     * @param queueName                  队列名
     * @param body                       消息体
     * @param <T>                        消息体类型
     * @throws IOException IOException
     */
    public <T extends Serializable> void publish(BuiltinExchangeType builtinExchangeType,
                                                 String queueName, T body) throws IOException {
        log.info("----------> 开始准备发布消息，消息体：{}", body.toString());
        ConnectionConfig connectionConfig = SpringBeanUtil.getBean(ConnectionConfig.class);
        //序列化消息体
        byte[] requestBody = SerializeUtil.serialize(body);
        ExchangeConfig exchangeConfig;
        switch (builtinExchangeType) {
            case DIRECT:
                exchangeConfig = new ExchangeConfig(ExchangeName.directExchangeName, builtinExchangeType);
                break;
            case FANOUT:
                exchangeConfig = new ExchangeConfig(ExchangeName.fanoutExchangeName, builtinExchangeType);
                break;
            default:
                exchangeConfig = new ExchangeConfig(ExchangeName.directExchangeName, builtinExchangeType);
        }
        if (channel == null) {
            Connection channelInstance = ConnectionUtil.getConnectionInstance(connectionConfig);
            channel = channelInstance.createChannel();
            // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
            channel.exchangeDeclare(exchangeConfig.getExchangeName(),
                    exchangeConfig.getBuiltinExchangeType(),
                    exchangeConfig.getPersistence(), exchangeConfig.getAutoDelete(),
                    exchangeConfig.getInnerExchange(), new HashMap<>());
            log.info("----------> 交换机声明成功，交换机名：{}", exchangeConfig.getExchangeName());
        }
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
        // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性, 消息属性, 消息体);
        channel.basicPublish(exchangeConfig.getExchangeName(), queueName, false, basicProperties, requestBody);
        log.info("----------> 消息发布成功，队列名：{}", queueName);
    }

    public static RabbitProducer instance() {
        if (rabbitProducer == null) {
            synchronized (RabbitProducer.class) {
                if (rabbitProducer == null) {
                    rabbitProducer = new RabbitProducer();
                }
            }
        }
        return rabbitProducer;
    }
}
