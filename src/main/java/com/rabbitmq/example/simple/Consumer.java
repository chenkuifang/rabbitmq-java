package com.rabbitmq.example.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 简单队列.消费者
 *
 * @author Quifar
 */
public class Consumer {

	// 需要监听的队列
	private static String QUEUE_NAME = "simple.queue.test";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = MqConnectionUtils.getConnection();
		Channel channel = connection.createChannel();

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" 接收到信息：" + message);
			}
		};

		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);

		channel.close();
		connection.close();

	}
}
