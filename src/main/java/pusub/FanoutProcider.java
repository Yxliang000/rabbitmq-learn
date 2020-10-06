package pusub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtils;

import java.io.IOException;

/**
 * 发布/订阅模型
 * fanout广播模式生产者
 */
public class FanoutProcider {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        // 创建连接
        Connection connection = RabbitmqUtils.getConnection();
        try {
            // 创建通道
            Channel channel = connection.createChannel();
            // 声明交换机
            // 参数一：交换机名称
            // 参数二：交换机类型
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // 发送消息到交换机
            // routingKey对于扇出交换，它的值将被忽略
            // 没有使用消息持久化，因为如果没有队列绑定到交换机，可以安全地丢弃该消息
            channel.basicPublish(EXCHANGE_NAME, "", null, "广播 fanout".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
