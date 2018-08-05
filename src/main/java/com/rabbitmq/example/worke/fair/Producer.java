package com.rabbitmq.example.worke.fair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 工作队列.公平分发.生产者
 *                         	   |------consumer1
 * producer ----> Queue -------|
 * 						       |------consumer2				
 *
 *1.需要改为手动回执
 *2.发消息的时候需要使用channel.basicQos(prefetchCount);
 *手动回执的情况下：消息处理快的，会得到更多的消息，(能者多劳)
 *
 * @author Quifar
 */
public class Producer {

	// 声明队列
	private static String QUEUE_NAME = "simple.queue.test";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

		Connection connection = MqConnectionUtils.getConnection();
		
		// 创建与消息代理的通信通道
		Channel channel = connection.createChannel();

		// 声明一个需要发送消息的队列
		// 注意：durable 是否持久化 ，如果可以声明了队列，不可以随意修改该值，需要重新声明队列就行了
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/**
		 * 每个消费者在确认消息前，消息队列一次只发一个消息
		 * 限制每个消费者在应答前，只能发送一条消息
		 */
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		// 发送消息
		for (int i = 0; i < 50; i++) {
			String msg = "hello msg" + i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.err.println("发送消息:" + msg);
		}

		channel.close();
		connection.close();
	}
}
