package workquene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.RabbitmqUtils;

import java.io.IOException;

public class WorkProvider {

    private static final String QUENE_NAME = "WorkQuene";

    public static void main(String[] args) {
        // 获取连接对象
        Connection connection = RabbitmqUtils.getConnection();
        try {
            // 获取通道
            Channel channel = connection.createChannel();
            // 通过通道声明队列
            channel.queueDeclare(QUENE_NAME, true, false, false, null);
            for (int i = 0; i < 1000; i++) {

                // 生产消息
                channel.basicPublish("", QUENE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                        ("第" + i + "次：hello work quene").getBytes());
            }
            //关闭通道和连接
            RabbitmqUtils.closeConnectionAndChannel(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
