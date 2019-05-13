package com.jack90john.conf;

import com.rabbitmq.client.BuiltinExchangeType;
import lombok.Data;

/**
 * Description: 交换机相关配置
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@Data
public class ExchangeConfig {

    private String exchangeName;                    //交换机名（默认：EXCHANGE_DIRECT_TEST）
    private BuiltinExchangeType builtinExchangeType;             //交换类型，具体类型见ExchangeTypeUtil（默认：direct）
    private Boolean persistence;                    //是否持久化（默认：true）
    private Boolean autoDelete;                     //是否自动删除（默认：false）
    private Boolean innerExchange;                  //是否内部交换机（默认：false）

    public ExchangeConfig(String exchangeName) {
        this.exchangeName = exchangeName;
        this.builtinExchangeType = BuiltinExchangeType.DIRECT;
        this.persistence = true;
        this.autoDelete = false;
        this.innerExchange = false;
    }

    public ExchangeConfig(String exchangeName, BuiltinExchangeType builtinExchangeType) {
        this.exchangeName = exchangeName;
        this.builtinExchangeType = builtinExchangeType;
        this.persistence = true;
        this.autoDelete = false;
        this.innerExchange = false;
    }

    public ExchangeConfig(String exchangeName, BuiltinExchangeType builtinExchangeType, Boolean persistence, Boolean autoDelete, Boolean innerExchange) {
        this.exchangeName = exchangeName;
        this.builtinExchangeType = builtinExchangeType;
        this.persistence = persistence;
        this.autoDelete = autoDelete;
        this.innerExchange = innerExchange;
    }

 }
