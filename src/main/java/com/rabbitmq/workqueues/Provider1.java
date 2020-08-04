package com.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Provider1 {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道对象
        Channel channel = connection.createChannel();
        //通过通道声明队列:队列名称：work1，队列不持久化，不独占队列，队列消费完后不自动删除，无额外参数
        channel.queueDeclare("work1", false, false, false, null);
        for (int i = 1; i <=20; i++) {
            //生产消息：无交换机，队列名称：work1,无传递消息额外设置，传递消息内容
            channel.basicPublish("", "work1", null, (i + "hello work queues").getBytes());
        }
        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
