package workquene;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WorkConsumer2 {

    private static final String QUENE_NAME = "WorkQuene";

    public static void main(String[] args) {
        Connection connection = RabbitmqUtils.getConnection();
        try {
            final Channel channel = connection.createChannel();
            channel.basicQos(1);
            channel.queueDeclare(QUENE_NAME, true, false, false, null);
            channel.basicConsume(QUENE_NAME, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        // 模拟处理消息繁忙
                        TimeUnit.SECONDS.sleep(2);
                        System.err.println("消费者2：" + new String(body));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
