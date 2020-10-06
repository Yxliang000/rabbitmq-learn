package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import utils.RabbitmqUtils;

public class Provider {

    private final static String QUEUE_NAME = "hello";

    /**
     * 生产消息
     */
    @Test
    public void providerMessage() {
        try {
            //获取连接对象
            Connection connection = RabbitmqUtils.getConnection();
            // 获取连接中的通道
            // Connection 和 Channel 都实现了 java.io.Closeable 不需要在代码中显示的关闭
            Channel channel = connection.createChannel();
            // 通道绑定对应的消息队列
            // 参数一：队列名称
            // 参数二：队列是否持久化
            // 参数三：是否独占队列，true:只允许当前连接可用
            // 参数四：消费后是否自动删除队列
            // 参数五：额外参数
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // 发布消息到具体的队列
            // 参数一：交换机名称（空字符串使用默认交换机）：接收生产者发送的消息并将这些消息路由给服务器中的队列，不同类型的Exchange转发消息的策略有所区别
            // 参数二：路由Key，队列名称
            // 参数三：设置消息持久化（并不能完全保证不会丢失消息，它只是保存到缓存中，而没有真正写入磁盘。持久性保证并不强）
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitMQ".getBytes());

            // 如果不显示关闭，将会一直监听
            RabbitmqUtils.closeConnectionAndChannel(channel, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
