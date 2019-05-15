# Rabbit MQ 消息发布/消费客户端

## 使用说明

1. 在项目中引入客户端
    ```xml
        <dependency>
            <groupId>com.jack90john</groupId>
            <artifactId>rabbitMQ_client</artifactId>
            <version>1.0.0</version>
        </dependency>
    ```
    
2. 在项目中增加rabbitMQ相关配置

    ```text
        #rabbitMQ
        connectionConfig.host=ip:port                      #主机地址（默认：localhost:5672）
        connectionConfig.virtualHost=/                     #虚拟消息服务器（默认：/）
        connectionConfig.username=***                      #用户名（默认：guest）
        connectionConfig.password=guest                    #密码（默认：guest）
        connectionConfig.automaticRecoveryEnabled=true     #网络异常重连（默认：true）
        connectionConfig.networkRecoveryInterval=5000      #重连间隔单位ms（默认：5000，即每5秒重试一次）
        
        #交换机配置（可选，但至少有一项）
        exchangeConfig.directExchangeName=***              #DRIECT模式交换机名
        exchangeConfig.fanoutExchangeName=***              #FANOUT模式交换机名
         
        #队列名称（参数名自定义，参数内容为rabbitMQ的root名）
        test.queue=test.queue
    ```
    
3. 使用客户端

```java
    //增加队列名称类
    @Component
    public class RabbitQueueNameTest {
    
        @Value("${test.queue}")
        private String testQueue;
    
        public String getTestQueue() {
            return testQueue;
        }
    
        public void setTestQueue(String testQueue) {
            this.testQueue = testQueue;
        }
    }
    
    //消息发布
    RabbitProducer.instance().publish(BuiltinExchangeType.DIRECT, queueName.getQueueTaskComplete(), "test");
    
    //消息消费
    RabbitConsumer.instance().consume(connectionConfig, rabbitQueueName.getQueueTaskComplete(), body -> {
                String str = SerializeUtil.deserialize(body);
                LOGGER.info("----------> 反序列化消息体成功，消息内容：{}", str);
            });
```
