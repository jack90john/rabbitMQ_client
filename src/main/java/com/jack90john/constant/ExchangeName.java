package com.jack90john.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@Component
public class ExchangeName {

    public static String directExchangeName;
    public static String fanoutExchangeName;

    @Value("${exchangeConfig.directExchangeName}")
    public void setDirectExchangeName(String directExchangeName) {
        ExchangeName.directExchangeName = directExchangeName;
    }

    @Value("${exchangeConfig.fanoutExchangeName}")
    public void setFanoutExchangeName(String fanoutExchangeName) {
        ExchangeName.fanoutExchangeName = fanoutExchangeName;
    }
}
