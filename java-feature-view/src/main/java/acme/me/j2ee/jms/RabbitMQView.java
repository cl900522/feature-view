package acme.me.j2ee.jms;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RabbitMQView {
    private static Logger logger = Logger.getLogger(RabbitMQView.class);
    private final static String QUEUE_NAME = "queue_001";
    private static final String TASK_QUEUE_NAME = "task_queue";

    @Autowired
    private ConnectionFactory factory;

    private Connection connection = null;

    @Before
    public void init() throws IOException {
        connection = factory.newConnection();
    }

    @Test
    public void test01CommonSendAndReceive() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test01ReceiveCommonMsg();
                } catch (Exception e) {
                }
            }
        }).start();
        Thread.sleep(500);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test01SendCommonMsg();
                } catch (Exception e) {
                }
            }
        }).start();
        Thread.sleep(1000);
    }

    public void test01SendCommonMsg() throws Exception {
        /* 建立Queue，如果存在则会忽略 */
        Channel channel = connection.createChannel();
        DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info("QueueName:" + queueDeclare.getQueue());
        logger.info("MessageCount:" + queueDeclare.getMessageCount());
        logger.info("ConsumerCount:" + queueDeclare.getConsumerCount());

        String message = "Hello there!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        logger.info("[" + QUEUE_NAME + "] Sent '" + message + "'");
    }

    public void test01ReceiveCommonMsg() throws Exception {
        /* 再次声明queue的存在，如果确认队的发送者已经存在，则可以忽略 */
        Channel channel = connection.createChannel();
        DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info("QueueName:" + queueDeclare.getQueue());
        logger.info("MessageCount:" + queueDeclare.getMessageCount());
        logger.info("ConsumerCount:" + queueDeclare.getConsumerCount());

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            logger.info("[" + QUEUE_NAME + "] Received '" + message + "'");
        }
    }

    @Test
    public void test02SendRecvWorkMsg() throws Exception {
        /* 两个worker逐个处理消息 */
        test02ReceiveWorkMsgAsync(1);
        test02ReceiveWorkMsgAsync(2);

        test02SendWorkMsg();

        Thread.sleep(2000);
    }

    public void test02SendWorkMsg() throws Exception {
        Channel channel = connection.createChannel();
        DeclareOk queueDeclare = channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        logger.info("QueueName:" + queueDeclare.getQueue());
        logger.info("MessageCount:" + queueDeclare.getMessageCount());
        logger.info("ConsumerCount:" + queueDeclare.getConsumerCount());

        int i = 20;
        while (i-- > 0) {
            String message = "WorkMessage:" + new Date();
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            logger.info("[" + TASK_QUEUE_NAME + "] Sent '" + message + "'");
        }
    }

    public void test02ReceiveWorkMsgAsync(final int taskId) throws Exception {
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info("Worker [" + taskId + "] from  queue [" + TASK_QUEUE_NAME + "] Received '" + message + "'");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                /* 手动ackkowledge */
                // getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        };
        /* 不自动acknowledge */
        channel.basicConsume(TASK_QUEUE_NAME, true, consumer);
    }

    @Test
    public void test03ExchangeFanout() throws Exception {
        String EXCHANGE_NAME = "logs", type = "fanout";
        declareExhange(EXCHANGE_NAME, type);

        test0xSubscribeMsg(EXCHANGE_NAME, "a");
        test0xSubscribeMsg(EXCHANGE_NAME, "b");
        test0xSubscribeMsg(EXCHANGE_NAME, "c");

        test0xPubishMsg(EXCHANGE_NAME, type, new String[] { "a", "b", "c" });
        Thread.sleep(1000);
    }

    @Test
    public void test04ExchangeDirect() throws Exception {
        String EXCHANGE_NAME = "events", type = "direct";
        declareExhange(EXCHANGE_NAME, type);

        test0xSubscribeMsg(EXCHANGE_NAME, "a");
        test0xSubscribeMsg(EXCHANGE_NAME, "b");
        test0xSubscribeMsg(EXCHANGE_NAME, "c");

        test0xPubishMsg(EXCHANGE_NAME, type, new String[] { "a", "b", "c" });
        Thread.sleep(1000);
    }

    @Test
    public void test05ExchangeTopic() throws Exception {
        String EXCHANGE_NAME = "match", type = "topic";
        declareExhange(EXCHANGE_NAME, type);

        test0xSubscribeMsg(EXCHANGE_NAME, "*.info");
        test0xSubscribeMsg(EXCHANGE_NAME, "*.error");
        test0xSubscribeMsg(EXCHANGE_NAME, "*.debug");

        test0xPubishMsg(EXCHANGE_NAME, type, new String[] { "a.info", "b.info", "c.debug" });
        Thread.sleep(1000);
    }

    public void declareExhange(String exchangeName, String type) throws Exception {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, type);
    }

    public void test0xPubishMsg(String exchangeName, String type, String[] routes) throws Exception {
        Channel channel = connection.createChannel();

        for (String route : routes) {
            String message = "Exchange Msg: " + route + ": " + System.currentTimeMillis();
            channel.basicPublish(exchangeName, route, null, message.getBytes());
            logger.info("Route [" + route + "] Exchange [" + exchangeName + "] Sent '" + message + "'");
        }
    }

    public void test0xSubscribeMsg(String exchangeName, final String route) throws Exception {
        final Channel channel = connection.createChannel();
        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, route);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info("Router [" + route + "] from queue [" + queueName + "] Received '" + message + "'");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    @After
    public void finish() throws IOException {
        connection.close();
    }
}
