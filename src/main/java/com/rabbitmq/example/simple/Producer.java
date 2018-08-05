package com.rabbitmq.example.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 简单队列.生产者
 *
 * @author Quifar
 */
public class Producer {

	// 声明队列
	private static String QUEUE_NAME = "simple.queue.test";

	public static void main(String[] args) throws IOException, TimeoutException {

		// 创建与消息代理的通信通道
		Connection connection = MqConnectionUtils.getConnection();
		Channel channel = connection.createChannel();

		// 声明一个需要发送消息的队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String msg = "hello simple rabbitmq message";

		// 发送消息
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

		System.err.println("发送消息:" + msg);

		channel.close();
		connection.close();

	}
}
