package acme.me.j2ee.jms;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActiveMQView {
    private static final Logger log = Logger.getLogger(ActiveMQView.class);

    private static Session session;
    // 消息发送到这个Queue
    private static Destination sendQueue;
    // 消息回复到这个Queue
    private static Destination replyQueue;

    @BeforeClass
    public static void init() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://192.168.100.200");
            Connection connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            sendQueue = session.createQueue("sendQueue");
            replyQueue = session.createQueue("replyQueue");
        } catch (Exception e) {
            System.out.println("System filed to init active sendQueue factory.");
        }
    }


    @Test
    public void test001ProducerAndConsumerTest() throws Exception {
        bindConsumerToReply();
        bindConsumerToSend();
        sendDataToSend();
        Thread.sleep(10000);
    }

    public void sendDataToSend() throws Exception {
        // 创建一个消息，并设置它的JMSReplyTo为replyQueue。
        MessageProducer producer = session.createProducer(sendQueue);
        log.info("Start to send first message");
        log.info("Start time is:" + new Date());
        for (int i = 0; i < 100; i++) {
            String sendMessageText = "Send message [" + i + "]";
            TextMessage message = session.createTextMessage(sendMessageText);
            message.setIntProperty("first", i);
            message.setIntProperty("second", i + 1);
            message.setJMSReplyTo(replyQueue);
            log.info(sendMessageText);

            producer.send(message);
        }
        log.info("Finish time is:" + new Date());
    }

    public void bindConsumerToSend() throws Exception {
        MessageConsumer comsumer = session.createConsumer(sendQueue);
        comsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                try {
                    TextMessage message = (TextMessage) m;
                    // 创建一个新的MessageProducer来发送一个回复消息。
                    MessageProducer producer = session.createProducer(m.getJMSReplyTo());

                    Integer result = message.getIntProperty("first") + message.getIntProperty("second");
                    producer.send(session.createTextMessage("Result is: " + result));
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void bindConsumerToReply() throws Exception {
        MessageConsumer comsumer2 = session.createConsumer(replyQueue);
        comsumer2.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                try {
                    log.info("Get reply: " + ((TextMessage) m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @AfterClass
    public static void finish() {
        try {
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
