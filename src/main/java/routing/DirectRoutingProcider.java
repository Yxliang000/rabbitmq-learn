package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtils;

import java.io.IOException;

public class DirectRoutingProcider {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {
        // 创建连接
        Connection connection = RabbitmqUtils.getConnection();
        try {
            // 创建通道
            Channel channel = connection.createChannel();
            // 声明交换机,参数一：交换机名称,参数二：交换机类型
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 发送消息到交换机

            // 发送 info 类型的消息
            channel.basicPublish(EXCHANGE_NAME, "info", null, "路由 direct：info".getBytes());

            // 发送 error 类型的消息
            channel.basicPublish(EXCHANGE_NAME, "error", null, "路由 direct：error".getBytes());

            // 发送 debug 类型的消息
            channel.basicPublish(EXCHANGE_NAME, "debug", null, "路由 direct：debug".getBytes());

            RabbitmqUtils.closeConnectionAndChannel(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
