package com.rabbitmq.example.publish_subscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 发布订阅模式.消费者1
 *
 * @author Quifar
 */
public class Consumer1 {

	// 交换机名称
	private static String EXCHANGE_NAME = "test.exchange";

	private static String QUEUE_NAME1 = "ps.queue1";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = MqConnectionUtils.getConnection();
		Channel channel = connection.createChannel();

		// 声明交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		// 声明队列 -- > 绑定队列到交换机
		channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
		
		// 绑定队列
		channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" 发布订阅.消费者1 ------ >接收到信息：" + message);
			}
		};

		// 监听队列
		channel.basicConsume(QUEUE_NAME1, true, consumer);
	}
}
