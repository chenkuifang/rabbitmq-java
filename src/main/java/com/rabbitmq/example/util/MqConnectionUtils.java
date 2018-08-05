package com.rabbitmq.example.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取链接
 *
 * @author Quifar
 */
public class MqConnectionUtils {

    /**
     * 
     * 获取Mq 链接
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        // 获取链接
        ConnectionFactory factory = new ConnectionFactory();

        // 设置ip、用户、密码
        factory.setHost("10.80.1.110");
        factory.setPort(5672);
        // vhost  类似数据库
        factory.setVirtualHost("/drugestore");
        factory.setUsername("km_user");
        factory.setPassword("123456");

        return factory.newConnection();
    }
}
