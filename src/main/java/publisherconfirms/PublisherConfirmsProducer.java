package publisherconfirms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtils;

import java.io.IOException;

public class PublisherConfirmsProducer {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {
        // 创建连接
        Connection connection = RabbitmqUtils.getConnection();
        try {
            // 创建通道
            Channel channel = connection.createChannel();
            //启用发布者确认
            channel.confirmSelect();

            // 声明交换机,参数一：交换机名称,参数二：交换机类型
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 发送消息到交换机
            channel.basicPublish(EXCHANGE_NAME, "log.info", null, "Topic：log.info".getBytes());
            channel.basicPublish(EXCHANGE_NAME, "log.error", null, "Topic：log.error".getBytes());
            channel.basicPublish(EXCHANGE_NAME, "log.info.error.haha", null, "Topic：log.info.error.haha".getBytes());

            RabbitmqUtils.closeConnectionAndChannel(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
