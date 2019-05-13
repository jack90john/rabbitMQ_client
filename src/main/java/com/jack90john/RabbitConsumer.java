package com.jack90john;

import com.jack90john.conf.ConnectionConfig;
import com.jack90john.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;

/**
 * Description: 消息消费者
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@Slf4j
public class RabbitConsumer implements Serializable {

    private static RabbitConsumer rabbitConsumer;

    private Channel channel;

    private RabbitConsumer() {
    }

    /**
     * 消息消费
     * @param queueName 队列名
     * @param rabbitListener 消费方式
     * @throws IOException IOException
     */
    public void consume(ConnectionConfig connectionConfig, String queueName, RabbitListener rabbitListener) throws IOException {
        Connection connection = ConnectionUtil.getConnectionInstance(connectionConfig);
        if (channel == null) {
            channel = connection.createChannel();
        }
        log.info("----------> 消息渠道获取成功。");
        //消息监听和消费
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    rabbitListener.listen(body);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicRecover();
                }

            }
        });
    }

    public static RabbitConsumer instance() {
        if (rabbitConsumer == null) {
            synchronized (RabbitConsumer.class) {
                if (rabbitConsumer == null) {
                    rabbitConsumer = new RabbitConsumer();
                }
            }
        }
        return rabbitConsumer;
    }
}
