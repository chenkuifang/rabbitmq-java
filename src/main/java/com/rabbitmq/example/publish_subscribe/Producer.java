package com.rabbitmq.example.publish_subscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 发布订阅模式.生产者
 *                         	   |--->queue1 --->consumer1
 * producer ----> exchange-----|
 * 						       |--->queue2 --->consumer2
 *
 * 1.发布订阅模式，如果消息到大交换机，没有路由到队列，则消息丢失，因为交换机没有存储能力，队列才有内存（硬盘）存储能力
 * 
 * 显现：根据交换器类型,通过routingkey 路由到对应的队列，可以同时消费一个消息
 *
 *
 * @author Quifar
 */
public class Producer {

	// 交换机名称
	private static String EXCHANGE_NAME = "test.exchange";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

		Connection connection = MqConnectionUtils.getConnection();

		// 创建与消息代理的通信通道
		Channel channel = connection.createChannel();

		// 声明一个交换机（交换机类型有：direct,header,topic,fanout）
		// fanout 类型 为不处理routingKey
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String msg = "hello publish subscribe message";
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

		System.out.println("发送消息成功： " + msg);

		channel.close();
		connection.close();
	}
}
