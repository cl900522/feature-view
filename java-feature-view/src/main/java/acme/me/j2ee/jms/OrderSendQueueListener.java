package acme.me.j2ee.jms;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class OrderSendQueueListener implements ChannelAwareMessageListener {
    private static Logger logger = Logger.getLogger(OrderSendQueueListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicQos(100);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
