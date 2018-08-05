package com.rabbitmq.example.worke.round;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 工作队列.轮询分发.生产者
 *                         	   |------consumer1
 * producer ----> Queue -------|
 * 						       |------consumer2				
 *
 *1.自动回执的情况下：消息会轮询转发给消费者
 *
 *
 *
 * @author Quifar
 */
public class Producer {

	// 声明队列
	private static String QUEUE_NAME = "simple.queue.test";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

		Connection connection = MqConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		// 创建与消息代理的通信通道

		// 声明一个需要发送消息的队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 发送消息
		for (int i = 0; i < 50; i++) {
			String msg = "hello msg" + i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.err.println("发送消息:" + msg);

			Thread.sleep(1000);
		}

		channel.close();
		connection.close();

	}
}
