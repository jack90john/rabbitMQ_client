package com.jack90john;

/**
 * Description:
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */

@FunctionalInterface
public interface RabbitListener {
    void listen(byte[] body);
}
