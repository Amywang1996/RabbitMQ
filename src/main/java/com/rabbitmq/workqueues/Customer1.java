package com.rabbitmq.workqueues;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);//一次只接受一条未确认的消息
        //通过通道声明队列:队列名称：work1，队列持久化，不独占队列，队列消费完后不自动删除，无额外参数
        channel.queueDeclare("work2", true, false, false, null);
        //参数1:队列名称 ，参数2:消息自动确认 true消费者自动向rabbitmq确认消息消费 ，false 不会自动确认消息
        //关闭自动确认，启动手动确认消息
        channel.basicConsume("work2", false, new DefaultConsumer(channel) {
            @Override //最后一个参数: 消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("消费者-1: " + new String(body));
                //手动确认  参数1:手动确认消息标识  参数2:false 每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
