package com.rabbitmq.utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Properties;
/**
 * RabbitMQ连接关闭工具类
 */
public class RabbitMQUtils {
    private static ConnectionFactory connectionFactory;
    private static Properties properties;
    static{
        //重量级资源  类加载只执行一次
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.46.102");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/study");
        connectionFactory.setUsername("Amy");
        connectionFactory.setPassword("Amy");
    }
    //定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //关闭通道和关闭连接工具方法
    public static void closeConnectionAndChanel(Channel channel, Connection conn) {
        try {
            if(channel!=null) channel.close();
            if(conn!=null)   conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
