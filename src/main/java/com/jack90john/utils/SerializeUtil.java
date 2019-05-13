package com.jack90john.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Description: 消息序列化与反序列化工具
 * Designer: jack
 * Date: 2019/1/11
 * Version: 1.0.0
 */
public class SerializeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化消息体
     * @param body 消息体
     * @param <T>  消息体类型
     * @return 已序列化消息
     */
    public static <T extends Serializable> byte[] serialize(T body){
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(body);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (IOException e) {
            LOGGER.info("消息序列化时发生IO异常，{}", e);
        }
        return bytes;
    }

    /**
     * 反序列化消息体
     * @param bytes 消息体
     * @param <T> 消息体类型
     * @return  反序列化结果
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(byte[] bytes){
        T t = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            t = (T) oi.readObject();
            bi.close();
            oi.close();
        } catch (IOException e) {
            LOGGER.info("消息反序列化时发生IO异常，{}", e);
        } catch (ClassNotFoundException e) {
            LOGGER.info("消息反序列化时未找到对应类，{}", e);
        }
        return t;
    }

}
