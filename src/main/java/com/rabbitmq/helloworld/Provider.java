package com.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.utils.RabbitMQUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式生产者
 */
public class Provider {
    //生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取连接中通道
        Channel channel = connection.createChannel();
        //通道绑定对应消息队列
        //参数1:  队列名称 如果队列不存在自动创建
        //参数2:  用来定义队列特性是否要持久化 true 持久化队列   false 不持久化
        //参数3:  exclusive 是否独占队列  true 独占队列   false  不独占
        //参数4:  autoDelete: 是否在消费完成后自动删除队列  true 自动删除  false 不自动删除
        //参数5:  额外附加参数
        channel.queueDeclare("hello",false,false,false,null);
        //发布消息
        //参数1: 交换机名称 参数2:队列名称  参数3:传递消息额外设置(MessageProperties.PERSISTENT_TEXT_PLAIN表示消息的持久化)  参数4:消息的具体内容
        channel.basicPublish("","hello", null,"hello rabbitmq".getBytes());
        //调用工具类，关闭连接
        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
