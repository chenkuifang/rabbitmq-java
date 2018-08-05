package com.rabbitmq.example.worke.fair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.example.util.MqConnectionUtils;

/**
 * 工作队列.公平分发.消费者
 *
 * @author Quifar
 */
public class Consumer1 {

	// 需要监听的队列
	private static String QUEUE_NAME = "simple.queue.test";

	public static void main(String[] args) throws IOException, TimeoutException {

		final Connection connection = MqConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" 消费者1-----接收到信息：" + message);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					// 手动应答
					channel.basicAck(envelope.getDeliveryTag(),false);
				}
				
				
			}
		};

		// 自动回执
		boolean autoAck = false;

		// 监听队列
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);

		// 不能关闭连接
	}
}
