package workquene;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

public class WorkConsumer1 {

    private static final String QUENE_NAME = "WorkQuene";

    public static void main(String[] args) {
        // 获取连接
        Connection connection = RabbitmqUtils.getConnection();
        try {
            // 获取通道
            final Channel channel = connection.createChannel();
            channel.basicQos(1);
            channel.queueDeclare(QUENE_NAME, true, false, false, null);
            channel.basicConsume(QUENE_NAME, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.err.println("消费者1：" + new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
