package pusub;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

/**
 * 发布/订阅模型
 * fanout广播模式生产者
 */
public class FanoutConsumer2 {

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
            // 创建一个非持久的，排他的，自动删除的临时队列
            String queueName = channel.queueDeclare().getQueue();

            // 将队列与交换机绑定
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            // 消费消息
            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.err.println("fanout消费者2：" + new String(body));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
