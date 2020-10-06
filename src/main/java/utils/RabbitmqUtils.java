package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqUtils {

    //创建连接MQ的连接工厂对象，类加载时只创建一次就够了
    private static ConnectionFactory factory;

    // 静态代码块，类加载时执行，只执行一次
    static {
        factory = new ConnectionFactory();
        factory.setHost("192.168.145.128");
        factory.setPort(5672);
        // 连接的虚拟主机
        factory.setVirtualHost("/ems");
        // 访问虚拟主机的用户名密码
        factory.setUsername("ems");
        factory.setPassword("123");
    }

    /**
     * 获取RabbitMQ 连接对象
     *
     * @return 连接对象
     */
    public static Connection getConnection() {
        try {
            //获取连接对象
            return factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 关闭通道、连接
     *
     * @param channel    通道
     * @param connection 连接对象
     */
    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
