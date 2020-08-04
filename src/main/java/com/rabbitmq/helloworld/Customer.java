package com.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.RabbitMQUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 简单模式消费者
 */
public class Customer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //通过工具类获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //通道绑定对象
        channel.queueDeclare("hello",false,false,false,null);
        //消费消息
        //参数1: 消费那个队列的消息 队列名称
        //参数2: 开始消息的自动确认机制
        //参数3: 消费时的回调接口
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            @Override //最后一个参数: 消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("===================================="+new String(body));
            }
        });
    }
}
