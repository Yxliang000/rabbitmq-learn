package helloworld;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            Connection connection = RabbitmqUtils.getConnection();
            // 获取连接中的通道
            // Connection 和 Channel 都实现了 java.io.Closeable 不需要在代码中显示的关闭
            Channel channel = connection.createChannel();

            // 通道绑定对应的消息队列，要和生产者参数配置一样
            // 参数一：队列名称
            // 参数二：持久化
            // 参数三：是否独占队列，true:只允许当前连接可用
            // 参数四：消费后是否自动删除队列
            // 参数五：额外参数
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // 消费消息
            // 参数一：队列名称
            // 参数二：开始消息的自动确认机制
            // 参数三：消费时的回调接口
            channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.err.println(new String(body));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
